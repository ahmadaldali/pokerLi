<script lang="ts">
  import type { TUserStory } from "$lib/shared/types/sprint";
  import LL from "$i18n/i18n-svelte";

  export let name: string;
  export let sequence: number[];
  export let creator: string;
  export let userStories: TUserStory[] = [];
  export let members: any[] = [];
  export let votingActive: boolean | null = null;

  $: revealedCount = userStories.filter((u) => u.isRevealed).length;
  $: uniqueVotersCount = new Set(userStories.flatMap((u) => u.voters ?? []))
    .size;
</script>

<header
  class="
    rounded-xl
    border border-white/10
    bg-slate-900/80
    px-6 py-4
    backdrop-blur
    gap-2
    flex flex-col
    w-full
  "
>
  <!-- Title -->
  <div class="flex items-center justify-start gap-2">
    <h1 class="text-xl font-semibold text-slate-100">
      {name}
    </h1>

    <div class="flex items-center gap-2 text-sm text-slate-400">
      {#if votingActive}
        <span class="text-emerald-400"> • {$LL.blocks.sprints.header.inProgress()} </span>
      {:else}
        <span class="text-slate-500"> • {$LL.blocks.sprints.header.noActiveVoting()} </span>
      {/if}
    </div>
  </div>

  <!-- Meta grid -->
  <div
    class="
      grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3
      gap-y-2 gap-x-6
      text-sm text-slate-400
    "
  >
    <div>
      <span class="font-medium text-slate-300">
        {userStories.length}
      </span>
      {$LL.blocks.sprints.header.userStories()}
    </div>

    <div>
      <span class="font-medium text-slate-300">
        {revealedCount}
      </span>
      {$LL.blocks.sprints.header.revealedStories()}
    </div>

    <div>
      <span class="font-medium text-slate-300">
        {members.length}
      </span>
      {$LL.blocks.sprints.header.membersInSprint()}
    </div>

    <div>
      <span class="font-medium text-slate-300">
        {uniqueVotersCount}
      </span>
      {$LL.blocks.sprints.header.uniqueVoters()}
    </div>

    <div>
      {$LL.blocks.sprints.header.createdBy()}
      <span class="font-medium text-slate-300">
        {creator}
      </span>
    </div>

    <div class="truncate">
      {$LL.blocks.sprints.header.sequence()}
      <span class="text-slate-300">
        {sequence.join(", ")}
      </span>
    </div>
  </div>
</header>
