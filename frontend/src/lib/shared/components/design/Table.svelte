<script lang="ts">
  import type { TTableConfig } from "$lib/shared/types/table";
  import Button from "./Button.svelte";

  export let config: TTableConfig<any>;
  export let rows: any[] = [];
  export let rowClass = "";
  export let emptyText = "No data available";

  const hasActions = !!config.actions?.length;
</script>

<div
  class="overflow-hidden rounded-2xl border border-white/10 bg-slate-900/80 backdrop-blur shadow-xl"
>
  <div class="overflow-x-auto">
    <table class="min-w-full table-fixed divide-y divide-white/10 text-sm">
      <!-- Column widths -->
      <colgroup>
        {#each config.columns as col}
          <col style={`width: ${col.width ?? "auto"}`} />
        {/each}

        {#if hasActions}
          <col style="width: 120px" />
        {/if}
      </colgroup>

      <!-- Header -->
      <thead class="bg-slate-800/60">
        <tr>
          {#each config.columns as col}
            <th
              class={`px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-slate-400 ${col.class ?? ""}`}
            >
              {col.label}
            </th>
          {/each}

          {#if hasActions}
            <th
              class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-slate-400"
            >
              Actions
            </th>
          {/if}
        </tr>
      </thead>

      <!-- Body -->
      <tbody class="divide-y divide-white/5">
        {#if rows.length === 0}
          <tr>
            <td
              colspan={config.columns.length + (hasActions ? 1 : 0)}
              class="px-6 py-10 text-center text-slate-400"
            >
              {emptyText}
            </td>
          </tr>
        {:else}
          {#each rows as row (row.id)}
            <tr class={`transition hover:bg-slate-800/60 ${rowClass}`}>
              {#each config.columns as col}
                <td class="px-4 py-3 text-slate-200 truncate">
                  {#if col.format}
                    {@html col.format(row[col.key], row)}
                  {:else}
                    {row[col.key]}
                  {/if}
                </td>
              {/each}

              {#if hasActions}
                <td class="px-4 py-3">
                  <div class="flex items-center gap-3">
                    {#each config.actions as action}
                      <Button
                        variant={action.variant ?? "ghost"}
                        on:click={() => action.onClick(row)}
                        disabled={action.disabled?.(row)}
                      >
                        {action.label}
                      </Button>
                    {/each}
                  </div>
                </td>
              {/if}
            </tr>
          {/each}
        {/if}
      </tbody>
    </table>
  </div>
</div>
