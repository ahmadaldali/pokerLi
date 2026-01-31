<script lang="ts">
  import LL from "$i18n/i18n-svelte";
  import StartNewVoting from "./StartNewVoting.svelte";

  export let sprintId: number;
</script>

<div
  class="flex flex-col items-center justify-center rounded-2xl
               border border-white/10 bg-slate-900/80 backdrop-blur
               p-10 text-center shadow-xl w-fit"
>
  <h2 class="mb-2 text-lg font-semibold text-white">{$LL.blocks.emptyUserStory.title()}</h2>
  <p class="mb-6 text-sm text-slate-400">
    {$LL.blocks.emptyUserStory.description()}
  </p>

  <div>
    <StartNewVoting {sprintId} />
  </div>
</div>
