<script lang="ts">
  import Button from "$components/design/Button.svelte";
  import { userStoriesApi } from "$lib/shared/api/user-story";
  import { EUserRole } from "$lib/shared/enums/user";
  import type { TApiResponse, TSuccessResponse } from "$lib/shared/types/http";
  import StartNewVoting from "./StartNewVoting.svelte";

  export let sprintId: number;
  export let userStoryId: number;
  export let voters: number[];
  export let isRevealed: boolean;
  export let notRevealedCount: number;
  export let userRole: EUserRole;

  let response: TApiResponse<TSuccessResponse> | null = null;

  async function reveal() {
    response = await userStoriesApi().reveal(userStoryId);
  }
</script>


  <div
    class="
    mx-auto my-10 max-w-md rounded-2xl
    border border-emerald-500/20
    bg-slate-900/80 backdrop-blur
    p-8 text-center shadow-xl
    {voters.length > 0 ? 'shadow-emerald-500/20' : 'shadow-black/30'}
  "
  >
    {#if !isRevealed}
      {#if voters.length > 0}
        <Button on:click={reveal} variant="primary" fullWidth={false}>
            Reveal cards
        </Button>
      {:else}
        <p class="text-sm font-medium text-slate-400">Pick your cards</p>
      {/if}
    {:else}
      <!--
  check if we don't have any active voting user story
  if you have jump to the next story
  // it means check other us in the same sprint which is not revealed yet
  <StartNewVoting {sprintId} />
  -->
      {#if notRevealedCount === 0}
        <StartNewVoting {sprintId} />
      {:else}
        <p class="text-sm font-medium text-slate-400">
          choose the next user story to vote on.
        </p>
      {/if}
    {/if}
  </div>

