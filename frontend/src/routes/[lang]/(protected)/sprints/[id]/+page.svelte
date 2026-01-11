<script lang="ts">
  import type { PageData } from "./$types";
  import { selectedUserStroyStore } from "$lib/shared/stores/vote";
  import type { TUserStory } from "$lib/shared/types/sprint";
  import CardSelector from "$components/vote/CardSelector.svelte";
  import Members from "$components/vote/Members.svelte";
  import { connect, disconnect } from "$lib/client/websocket/vote";
  import { onDestroy, onMount } from "svelte";

  export let data: PageData;

  const { sprintResponse, sprint, user } = data;

  function selectUserStory(story: TUserStory) {
    // logic to select a user story for voting
    selectedUserStroyStore.set(story);
  }

  $: userStories = sprint?.userStories || [];
  $: members = sprint?.members || [];
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
        <!-- Empty state -->
        <div
          class="flex flex-col items-center justify-center rounded-2xl
               border border-white/10 bg-slate-900/80 backdrop-blur
               p-10 text-center shadow-xl"
        >
          <h2 class="mb-2 text-lg font-semibold text-white">
            No user stories yet
          </h2>
          <p class="mb-6 text-sm text-slate-400">
            Start a new voting session to estimate your first story.
          </p>

          <button
            on:click={startNewVoting}
            class="rounded-lg bg-emerald-500 px-6 py-2.5
                 font-semibold text-slate-950
                 hover:bg-emerald-400 transition
                 focus:outline-none focus:ring-2 focus:ring-emerald-500
                 focus:ring-offset-2 focus:ring-offset-slate-900"
          >
            Start new voting
          </button>
        </div>
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

            <div class="px-6 py-8">
              {#if $selectedUserStroyStore}
                selected: {$selectedUserStroyStore?.name}

                {#if $selectedUserStroyStore.isRevealed}
                  vote again
                {:else}
                  
                  <button on:click={() => {
                    $selectedUserStroyStore.isRevealed = true;
                    selectedUserStroyStore.set($selectedUserStroyStore);
                  }}>
                    reveal
                  </button>
                {/if}

                <Members
                  {members}
                  isRevealed={$selectedUserStroyStore.isRevealed}
                />

                <CardSelector
                  sequeceElements={sprint.sequence}
                  userStoryId={$selectedUserStroyStore.id}
                  userId={user.id}
                  bind:estimations={$selectedUserStroyStore.estimations}
                  isRevealed={$selectedUserStroyStore.isRevealed}
                />
              {:else}
                <!-- Placeholder for voting UI -->
                <div
                  class="flex h-64 flex-col items-center justify-center
                     text-center text-slate-400"
                >
                  <p class="mb-2 text-sm">
                    Select a user story to begin voting
                  </p>
                  <p class="text-xs">
                    Voting cards, timer, and participants will appear here.
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
