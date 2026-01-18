<script lang="ts">
  import Button from "$components/design/Button.svelte";
  import StartNewVoting from "./StartNewVoting.svelte";

  export let sprintId: number;
</script>

<div
  class="flex flex-col items-center justify-center rounded-2xl
               border border-white/10 bg-slate-900/80 backdrop-blur
               p-10 text-center shadow-xl w-fit"
>
  <h2 class="mb-2 text-lg font-semibold text-white">No user stories yet</h2>
  <p class="mb-6 text-sm text-slate-400">
    Start a new voting session to estimate your first story.
  </p>

  <div>
    <StartNewVoting sprintId={sprintId} />
  </div>
</div>
