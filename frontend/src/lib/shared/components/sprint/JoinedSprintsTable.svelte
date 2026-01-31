<script lang="ts">
  import Table from "$components/design/Table.svelte";
  import type { TSprint } from "$lib/shared/types/sprint";
  import type { TTableConfig } from "$lib/shared/types/table";
  import { sprintUtils } from "$lib/shared/utils/sprint";
  import LL from "$i18n/i18n-svelte";

  export let sprints: TSprint[];

  export function getTableConfig(ll: typeof $LL): TTableConfig<TSprint> {
    return {
      columns: sprintUtils().getSprintTableColumns(ll),
      actions: sprintUtils().getJoinedSprintTableActions(ll),
    };
  }
</script>

<section class="space-y-4">
  <div class="flex items-center justify-between">
    <h2 class="text-lg font-semibold text-white">
      {$LL.blocks.sprints.joined()}
    </h2>
  </div>

  <div class="rounded-xl border border-white/10 bg-white/5 p-4">
    <Table
      rows={sprints}
      config={getTableConfig($LL)}
      emptyText={$LL.tables.sprints.empty()}
      actionsTitle={$LL.tables.sprints.actions.title()}
    />
  </div>
</section>
