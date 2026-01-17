<script lang="ts">
  import type { TUserStoryEstimation } from "$lib/shared/types/sprint";
  import type { TUser } from "$lib/shared/types/user";

  export let members: TUser[];
  export let voters: number[];
  export let isRevealed: boolean;
  export let estimations: TUserStoryEstimation[] | undefined;
  export let lastEstimationResultId: number | undefined;

  /**
   * Reactive lookup table:
   * userId -> estimation
   */
  let estimationByUser = new Map<number, TUserStoryEstimation>();

  $: {
    estimationByUser = new Map();

    if (estimations) {
      for (const est of estimations) {
        const shouldInclude =
          (isRevealed && est.estimationResultId === lastEstimationResultId) ||
          (!isRevealed && est.estimationResultId == null);

        if (shouldInclude) {
          estimationByUser.set(est.user.id, est);
        }
      }
    }
  }
</script>

<div class="px-6 py-8">
  <div
    class="grid grid-cols-2 gap-6 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6"
  >
    {#each members as member (member.id)}
      {@const estimation = estimationByUser.get(member.id)}

      <div class="flex flex-col items-center gap-2">
        <!-- CARD -->
        <div
          class="
            relative flex h-20 w-14 items-center justify-center rounded-xl
            border text-lg font-semibold overflow-hidden
            {isRevealed
            ? 'border-emerald-500 bg-emerald-500/10 text-emerald-400'
            : 'border-emerald-500/40 bg-emerald-500/10'}
          "
        >
          {#if isRevealed}
            {estimation?.estimation ?? "–"}
          {:else if voters.includes(member.id)}
            <!-- shimmer -->
            <div
              class="absolute inset-0 animate-pulse
                  bg-[linear-gradient(120deg,transparent,rgba(16,185,129,0.4),transparent)]"
            >
              <span class="relative select-none text-transparent">0</span>
            </div>
          {:else}
            <!-- hidden -->
            <div class="absolute inset-0 bg-slate-700">
              <span class="relative select-none text-transparent">0</span>
            </div>
          {/if}
        </div>

        <!-- USER NAME -->
        <p class="text-center text-xs text-slate-400">
          {member.name}
        </p>
      </div>
    {/each}
  </div>
</div>
