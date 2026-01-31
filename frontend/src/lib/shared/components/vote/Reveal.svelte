<script lang="ts">
  import Button from "$components/design/Button.svelte";
  import { userStoriesApi } from "$lib/shared/api/user-story";
  import { EUserRole } from "$lib/shared/enums/user";
  import type { TApiResponse, TSuccessResponse } from "$lib/shared/types/http";
  import StartNewVoting from "./StartNewVoting.svelte";
  import LL from "$i18n/i18n-svelte";

  export let sprintId: number;
  export let userStoryId: number;
  export let voters: number[];
  export let isRevealed: boolean;
  export let notRevealedCount: number;

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
        {$LL.blocks.reveal.title()}
      </Button>
    {:else}
      <p class="text-sm font-medium text-slate-400">{$LL.blocks.reveal.text()}</p>
    {/if}
  {:else}
    {#if notRevealedCount === 0}
      <StartNewVoting {sprintId} />
    {:else}
      <p class="text-sm font-medium text-slate-400">
        {$LL.blocks.reveal.chooseNextUserStory()}
      </p>
    {/if}
  {/if}
</div>
