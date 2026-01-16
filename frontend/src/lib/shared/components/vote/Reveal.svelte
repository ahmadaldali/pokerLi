<script lang="ts">
  import * as api from "$lib/shared/api/vote";
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
    response = await api.reveal(userStoryId);
  }
</script>

{#if userRole !== EUserRole.GUEST}
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
        <button
          on:click={reveal}
          class="
          inline-flex items-center justify-center gap-2
          rounded-lg bg-emerald-500 px-6 py-3
          font-semibold text-slate-950
          transition
          hover:bg-emerald-400
          focus:outline-none focus:ring-2 focus:ring-emerald-500
        "
        >
          Reveal cards
        </button>
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
{/if}
