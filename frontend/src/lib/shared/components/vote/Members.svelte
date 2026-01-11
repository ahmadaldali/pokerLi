<script lang="ts">
  import type { TUser } from "$lib/shared/types/user";

  export let members: TUser[];
  export let isRevealed: boolean;
</script>

<div class="px-6 py-8">
  <div
    class="grid grid-cols-2 gap-6 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6"
  >
    {#each members as member}
      <div class="flex flex-col items-center gap-2">
        <!-- CARD -->
        {#if isRevealed}
          <div
            class="flex h-20 w-14 items-center justify-center rounded-xl
         border border-emerald-500 bg-emerald-500/10
         text-lg font-semibold text-emerald-400"
          >
            3
          </div>
        {:else}
          <div
            class="
    flex h-20 w-14 items-center justify-center rounded-xl
    border border-emerald-500/40
    bg-emerald-500/10
    relative overflow-hidden
  "
          >
            <!-- subtle animated shimmer -->
            <div
              class="absolute inset-0
           bg-[linear-gradient(120deg,transparent,rgba(16,185,129,0.4),transparent)]
           animate-pulse"
            ></div>

            <!-- hidden value -->
            <span class="relative text-transparent select-none">0</span>
          </div>
        {/if}

        <!-- USER NAME -->
        <p class="max-w-[5rem] truncate text-xs text-slate-400 text-center">
          {member.name}
        </p>
      </div>
    {/each}
  </div>
</div>
