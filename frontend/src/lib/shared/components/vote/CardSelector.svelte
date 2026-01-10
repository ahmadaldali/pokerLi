<script lang="ts">
  import { api } from "$lib/shared/api/http";
  import { vote } from "$lib/shared/api/vote";
  import { estimationStore } from "$lib/shared/stores/vote";

  export let sequeceElements: number[];
  export let userStoryId: number;

  async function handleEstimateClick(estimateValue: number) {
    // un vote if the same value is clicked

    const response = await vote(userStoryId, {
      estimation: estimateValue,
    });

    console.log("Vote response:", response);

    if (response.success) {
      estimationStore.set(
        $estimationStore === estimateValue ? null : estimateValue
      );
    } else {
      console.error("Error voting:", response.result);
    }
  }
</script>

<div class="flex justify-center">
  <div class="grid grid-cols-3 gap-4 sm:grid-cols-4 md:grid-cols-6">
    {#each sequeceElements as element}
      <button
        class="group relative"
        on:click={() => handleEstimateClick(element)}
      >
        <div
          class="
            flex h-20 w-16 items-center justify-center rounded-xl
            border bg-slate-900 text-lg font-semibold
            transition-all duration-100
            hover:-translate-y-2
            focus:outline-none
            {$estimationStore === element
            ? 'border-emerald-500 bg-emerald-400 text-slate-200 ring-emerald-500/30 -translate-y-3 '
            : 'border-slate-800 text-slate-200'}
          "
        >
          {element}
        </div>
      </button>
    {/each}
  </div>
</div>
