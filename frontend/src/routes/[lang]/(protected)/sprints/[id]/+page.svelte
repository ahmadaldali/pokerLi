<script lang="ts">
  import type { PageData } from "./$types";
  import type { TUserStory } from "$lib/shared/types/sprint";
  import { connect, disconnect } from "$lib/client/websocket/vote";
  import { onMount, onDestroy } from "svelte";

  import CardSelector from "$components/vote/CardSelector.svelte";
  import Members from "$components/vote/Members.svelte";
  import EmptyUserStories from "$components/vote/EmptyUserStories.svelte";
  import Reveal from "$components/vote/Reveal.svelte";
  import UserStoryResults from "$components/vote/UserStoryResults.svelte";
  import CreateGuestModal from "$components/modal/CreateGuestModal.svelte";
  import Button from "$components/design/Button.svelte";
  import { userStoriesApi } from "$lib/shared/api/user-story";

  export let data: PageData;

  const { sprintResponse, sprint, user } = data;

  let userStories: TUserStory[] = sprint?.userStories ?? [];
  let members = sprint?.members ?? [];
  let activeVotingUserStory: TUserStory | undefined;

$: activeVotingUserStory = userStories.find((s) => s.isActive);

  function updateUserStory(updated: TUserStory) {
    const index = userStories.findIndex((u) => u.id === updated.id);
    if (index === -1) return;

    // immutable update (important!)
    userStories = userStories.map((u, i) => (i === index ? updated : u));
  }

  onMount(() => {
    connect(
      // single user story update
      (userStory) => {
        console.log("Received user story update via websocket", userStory);
        updateUserStory(userStory);
      },

      // full sprint update
      (updatedSprint) => {
        if (updatedSprint.id !== sprint?.id) return;

        // update stories safely
        for (const us of updatedSprint.userStories ?? []) {
          updateUserStory(us);
        }

        members = updatedSprint.members ?? [];
      }
    );
  });

  onDestroy(() => {
    disconnect();
  });

  async function selectUserStory(userStoryId: number) {
    await userStoriesApi().select(userStoryId);
  }
</script>

<div class="px-6 py-8 md:px-10 flex flex-col gap-4">
  {#if sprint}
    <div class="mb-8">
      <p class="text-sm text-slate-400">{sprint.name}</p>
    </div>

    {#if user}
      {#if userStories.length === 0}
        <EmptyUserStories sprintId={sprint.id} />
      {:else}
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <!-- LEFT: STORIES -->
          <aside class="rounded-2xl border border-white/10 bg-slate-900/80">
            <div class="border-b border-white/10 px-4 py-3">
              <h2 class="text-sm font-semibold text-slate-400 uppercase">
                User stories
              </h2>
            </div>

            <ul class="divide-y divide-white/5">
              {#each userStories as story}
                {#key story.id}
                <li class="px-4 py-3 hover:bg-slate-800/60 text-slate-200">
                  <p class="font-medium">{story.id}</p>
                  <p class="text-xs text-slate-400 truncate">
                    {story.description}
                  </p>

                  {#if activeVotingUserStory?.id === story.id}
                    <span
                      class="
                      mt-2 inline-block rounded-full bg-emerald-500/20
                      px-2 py-0.5 text-xs font-semibold text-emerald-400
                    "
                    >
                      Voting this issue
                    </span>
                  {:else}
                    <Button
                      variant="outline"
                      fullWidth={false}
                      on:click={() => selectUserStory(story.id)}
                    >
                      vote this issue
                    </Button>
                  {/if}
                  </li>
                {/key}
         
              {/each}
            </ul>
          </aside>

          <!-- RIGHT: ACTIVE VOTING -->
          <section
            class="lg:col-span-2 rounded-2xl border text-slate-200 border-white/10 bg-slate-900/80 backdrop-blur shadow-xl"
          >
            <div class="border-b border-white/10 px-6 py-4">
              <h2 class="text-lg font-semibold">
                Active voting {activeVotingUserStory?.id}
              </h2>
            </div>

            <div class="p-6">
              {#if activeVotingUserStory}
                {@const last = activeVotingUserStory.estimationResults?.[0]}

           <p class="bg-red-700">   est {last?.id}</p>

                <Members
                  {members}
                  voters={activeVotingUserStory.voters || []}
                  isRevealed={activeVotingUserStory.isRevealed}
                  lastEstimationResultId={last?.id}
                  estimations={activeVotingUserStory.estimations}
                />

                <Reveal
                  sprintId={sprint.id}
                  userStoryId={activeVotingUserStory.id}
                  voters={activeVotingUserStory.voters || []}
                  isRevealed={activeVotingUserStory.isRevealed}
                  notRevealedCount={userStories.filter((u) => !u.isRevealed)
                    .length}
                  userRole={user.role}
                />

               {#if activeVotingUserStory?.isRevealed}
                  {#key activeVotingUserStory.id}
                    <UserStoryResults
                      estimate={last?.estimation}
                      count={last?.count}
                    />
                  {/key}
                {:else}
                  <CardSelector
                    sequeceElements={sprint.sequence}
                    userStoryId={activeVotingUserStory.id}
                    userId={user.id}
                    estimations={activeVotingUserStory.estimations}
                  />
                {/if}
              {:else}
                <p class="text-center text-slate-400">
                  Select a user story to begin voting
                </p>
              {/if}
            </div>
          </section>
        </div>
      {/if}
    {/if}
  {:else if sprintResponse.result.error === "UN_AUTHORIZED"}
    <CreateGuestModal formData={data.form} />
  {:else}
    <p>Error loading sprint.</p>
    <p>{sprintResponse.result.error}</p>
  {/if}
</div>
