<script lang="ts">
  import type { TUserStoryEstimation } from "$lib/shared/types/sprint";
  import { toggleEstimationVote } from "$lib/shared/utils/vote";

  export let sequeceElements: number[];
  export let userStoryId: number;
  export let estimations: TUserStoryEstimation[] | undefined;
  export let userId: number;

  async function handleEstimateClick(value: number) {
    const response = await toggleEstimationVote(
      userStoryId,
      value,
      estimationValue
    );

    if (response.apiResponse.success) {
      estimationValue = response.newValue;

    }
  }
  
  $: if (estimations) {
    estimationValue =
      estimations?.find((est) => est.user.id === userId && est.estimationResultId === null)?.estimation || null;
  }

  let estimationValue: number | null = null;
</script>


<div class="flex justify-center flex-col items-center space-y-4">
  <p class="text-sm text-slate-400">Choose your card 👇</p>

  <div class="grid grid-cols-3 gap-4 sm:grid-cols-4 md:grid-cols-6">

        
      {#each sequeceElements as element}
        {#key element}
        <button
          class="group relative"
          on:click={() => handleEstimateClick(element)}
        >
          <div
            class="
            flex h-20 w-16 items-center justify-center rounded-xl
            border text-lg font-semibold
            transition-all duration-100
            hover:-translate-y-2
            focus:outline-none
            {estimationValue === element
              ? 'border-emerald-500 bg-emerald-600 text-slate-200 ring-emerald-500/30 -translate-y-3 '
              : 'border-slate-800 text-slate-200 bg-slate-900'}
          "
          >
            {element}
          </div>
        </button>
        {/key}
      {/each}
  </div>
</div>
