<script lang="ts">
  import type { PageData } from "./$types";
  import type { TUserStory } from "$lib/shared/types/sprint";
  import CardSelector from "$components/vote/CardSelector.svelte";
  import Members from "$components/vote/Members.svelte";
  import { connect, disconnect } from "$lib/client/websocket/vote";
  import { onDestroy, onMount } from "svelte";
  import EmptyUserStories from "$components/vote/EmptyUserStories.svelte";
  import Reveal from "$components/vote/Reveal.svelte";
  import UserStoryResults from "$components/vote/UserStoryResults.svelte";

  export let data: PageData;

  const { sprintResponse, sprint, user } = data;

  function selectUserStory(story: TUserStory) {
    // logic to select a user story for voting
  }

  $: userStories = sprint?.userStories || [];
  $: members = sprint?.members || [];

  $: activeVotingUserStroy = userStories.find((story) => story.isActive);

  onMount(() => {
    connect(
      (userStory) => {
        const index = userStories.findIndex((us) => us.id === userStory.id);
        if (index !== -1) {
          userStories[index] = userStory;
          userStories = [...userStories];
        }
      },
      (updatedSprint) => {
        if (updatedSprint.id === sprint?.id) {
          userStories = updatedSprint.userStories || [];
          members = updatedSprint.members || [];
        }
      }
    );
  });

  onDestroy(() => {
    disconnect();
  });
</script>

<div class="px-6 py-8 md:px-10">
  <!-- User header -->
  <div class="mb-6 flex items-center justify-between">
    <div>
      <p class="text-sm text-slate-400">Signed in as</p>
      <p class="font-medium text-white">
        {user?.name}
        <span class="text-slate-400 text-sm">({user?.email})</span>
      </p>
    </div>
  </div>

  {#if sprint}
    <!-- Sprint header -->
    <div class="mb-8">
      <h1 class="text-2xl font-semibold text-white">
        {sprint.name}
      </h1>
      <p class="mt-1 text-sm text-slate-400">
        Sprint ID: {sprint.id}
      </p>
    </div>

    {#if user}
      {#if userStories.length === 0}
        <EmptyUserStories sprintId={sprint.id} />
      {:else}
        <!-- Voting layout -->
        <div class="grid grid-cols-1 gap-6 lg:grid-cols-3">
          <!-- Left: user stories list -->
          <aside
            class="lg:col-span-1 rounded-2xl border border-white/10
                 bg-slate-900/80 backdrop-blur shadow-xl"
          >
            <div class="border-b border-white/10 px-4 py-3">
              <h2
                class="text-sm font-semibold uppercase tracking-wide text-slate-400"
              >
                User stories
              </h2>
            </div>

            <ul class="divide-y divide-white/5">
              {#each userStories as story}
                <li
                  class="cursor-pointer px-4 py-3 text-sm
                       text-slate-200 hover:bg-slate-800/60 transition"
                >
                  <p class="font-medium">{story.name}</p>
                  <p class="text-xs text-slate-400 truncate">
                    {story.description}
                  </p>
                </li>

                <button on:click={() => selectUserStory(story)}
                  >select us
                </button>
              {/each}
            </ul>
          </aside>

          <!-- Right: active voting -->
          <section
            class="lg:col-span-2 rounded-2xl border border-white/10
                 bg-slate-900/80 backdrop-blur shadow-xl"
          >
            <div class="border-b border-white/10 px-6 py-4">
              <h2 class="text-lg font-semibold text-white">Active voting</h2>
            </div>

            <div class="px-6 py-8 flex flex-col items-center">
              {#if activeVotingUserStroy}
                <p class="text-sm text-slate-400">
                  {activeVotingUserStroy.name}
                </p>

                <Members
                  {members}
                  isRevealed={activeVotingUserStroy.isRevealed}
                />

                <Reveal
                  sprintId={sprint.id}
                  userStoryId={activeVotingUserStroy.id}
                  voters={activeVotingUserStroy.voters || []}
                  isRevealed={activeVotingUserStroy.isRevealed}
                  notRevealedCount={userStories.filter((us) => !us.isRevealed)
                    .length}
                />

                {#if activeVotingUserStroy.isRevealed}
                  <UserStoryResults
                    estimate={activeVotingUserStroy.estimationResults?.[0]?.estimation}
                    count={activeVotingUserStroy.estimationResults?.[0]?.count}
                  />
                {:else}
                  <CardSelector
                    sequeceElements={sprint.sequence}
                    userStoryId={activeVotingUserStroy.id}
                    userId={user.id}
                    bind:estimations={activeVotingUserStroy.estimations}
                  />
                {/if}
              {:else}
                <!-- Placeholder for voting UI -->
                <div
                  class="flex h-64 flex-col items-center justify-center
                     text-center text-slate-400"
                >
                  <p class="mb-2 text-sm">
                    Select a user story to begin voting
                  </p>
                </div>
              {/if}
            </div>
          </section>
        </div>
      {/if}
    {/if}
  {:else}
    <h1>Error Loading Sprint</h1>
    <p>There was an error loading the sprint details.</p>
    <pre>{JSON.stringify(sprintResponse.result.error, null, 2)}</pre>
  {/if}
</div>
