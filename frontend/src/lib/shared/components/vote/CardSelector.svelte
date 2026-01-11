<script lang="ts">
  import { connect, disconnect } from "$lib/client/websocket/vote";
  import { api } from "$lib/shared/api/http";
  import { unVote, vote } from "$lib/shared/api/vote";
  import {
    userStoryEstimationStore,
    updateEstimation,
  } from "$lib/shared/stores/vote";
  import type { TApiResponse, TSuccessResponse } from "$lib/shared/types/http";
  import type {
    TUserStory,
    TUserStoryEstimation,
  } from "$lib/shared/types/sprint";
  import { onDestroy, onMount } from "svelte";

  export let sequeceElements: number[];
  export let userStoryId: number;
  export let estimations: TUserStoryEstimation[] | undefined;
  export let userId: string;
  export let isRevealed: boolean;


  $: console.log("userStoryId:", userStoryId);


  async function handleEstimateClick(value: number) {
    // un vote if the same value is clicked

    let newStoreValue = value as number | null;

    let response: TApiResponse<TSuccessResponse> | null = null;
    if (value === $userStoryEstimationStore) {
      response = await unVote(userStoryId);
      newStoreValue = null;
    } else {
      response = await vote(userStoryId, {
        estimation: value,
      });
      newStoreValue = value;
    }

    if (response.success) {
      updateEstimation(newStoreValue);
    } else {
      console.error("Error voting:", response.result);
    }
  }

  $: if (userStoryId) {
        const estimationValue = estimations?.find(
      (est) => est.user.id === userId
    )?.estimation;
    updateEstimation(estimationValue ?? null);
  }

  onMount(() => {
      console.log("mooo", estimations)

    connect((data: TUserStory) => {
      console.log("Received update for user story:", data);
      estimations = data.estimations;

      const estimationValue = estimations?.find(
        (est) => est.user.id === userId
      )?.estimation;

      updateEstimation(estimationValue ?? null);
    });
  });

  onDestroy(() => {
    disconnect();
  });
</script>

<div class="flex justify-center">
  <div class="grid grid-cols-3 gap-4 sm:grid-cols-4 md:grid-cols-6">
    {#if isRevealed}

    isRevealed .....
    {:else}
    {#each sequeceElements as element}
      <button
        class="group relative"
        on:click={() => handleEstimateClick(element)}
      >
        <div
          class="
            flex h-20 w-16 items-center justify-center rounded-xl
            border  text-lg font-semibold
            transition-all duration-100
            hover:-translate-y-2
            focus:outline-none
            {$userStoryEstimationStore === element
            ? 'border-emerald-500 bg-emerald-600 text-slate-200 ring-emerald-500/30 -translate-y-3 '
            : 'border-slate-800 text-slate-200 bg-slate-900'}
          "
        >
          {element}
        </div>
      </button>
    {/each}

    {/if}
  </div>
</div>
