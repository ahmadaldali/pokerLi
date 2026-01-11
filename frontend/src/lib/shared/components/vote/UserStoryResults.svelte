<script lang="ts">
  import { Confetti } from "svelte-confetti";

  // undefined means not revealed yet
  export let estimate: number | undefined;
  export let count: number | undefined;
</script>

<div
  class="
    relative mx-auto max-w-sm rounded-2xl
    border border-emerald-500/20
    bg-slate-900/90 backdrop-blur
    p-6 px-2 text-center shadow-xl shadow-emerald-500/10
  "
>
  <!-- Confetti -->
  <div class="absolute bottom-0 justify-center w-full pointer-events-none items-center flex">
    <Confetti delay={[0, 1000]} size={15} amount={100} />
  </div>

  <!-- Content -->
  <div class="relative space-y-4">
    <p class="text-sm text-slate-400">Average estimate</p>
    <p class="text-4xl font-bold text-emerald-400">
      {estimate ?? "—"}
    </p>

    <div class="h-px bg-slate-800"></div>

    <p class="text-sm text-slate-400">
      {count ?? 0} voters
    </p>
  </div>
</div>
