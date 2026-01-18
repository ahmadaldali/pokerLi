<script lang="ts">
  import Button from "$components/design/Button.svelte";
  import { fade } from "svelte/transition";

  export let id: number;
  export let name: string | null;
  export let description: string;
  export let active = false;
  export let isRevealed = false;
  export let onVote: (id: number) => void;
</script>

<li transition:fade={{ duration: 120 }}>
  <div class="flex justify-between items-center gap-4 m-2">
    <!-- Text -->
    <div class="flex flex-col justify-center gap-2">
      <div class="flex justify-start items-center gap-4">
        <span class="font-medium text-slate-200">
          {name ?? "Generic"}
        </span>
      </div>

      <span class="text-sm text-slate-400">
        {id} — {description}
      </span>
    </div>

    <!-- Status -->
    {#if active}
      <span
        class="
            shrink-0 rounded-full
            bg-emerald-500/20
            px-2 py-0.5
            text-xs font-semibold
            text-emerald-400
          "
      >
        Voting now
      </span>
    {:else}
      <Button
        fullWidth={false}
        variant="outline"
        class="transition
                hover:bg-slate-800/60
                focus-within:bg-slate-800/60
                cursor-pointer select-none"
        on:click={() => !active && onVote(id)}
      >
        {isRevealed ? "Vote again" : "Vote this story"}
      </Button>
    {/if}
  </div>
</li>
