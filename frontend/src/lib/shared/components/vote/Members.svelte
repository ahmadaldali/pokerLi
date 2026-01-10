<script lang="ts">
    export let members: Array<{
        id: number;
        full_name: string;
    }>;
</script>

<div class="px-6 py-8">
  <div
    class="grid grid-cols-2 gap-6 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6"
  >
    {#each members as member}
      <div class="flex flex-col items-center gap-2">

        <!-- CARD -->
        {#if false}
          <div
            class="
              flex h-20 w-14 items-center justify-center rounded-xl
              border text-lg font-semibold transition
           "
          >
            3
          </div>
        {:else}
          <div
            class="
              flex h-20 w-14 items-center justify-center rounded-xl
              border border-slate-800 bg-slate-900 text-slate-600
            "
          >
            —
          </div>
        {/if}

        <!-- USER NAME -->
        <p class="max-w-[5rem] truncate text-xs text-slate-400 text-center">
          {member.full_name}
        </p>
      </div>
    {/each}
  </div>
</div>
