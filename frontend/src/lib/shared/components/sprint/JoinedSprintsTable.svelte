<script lang="ts">
  import Table from "$components/design/Table.svelte";
  import type { TSprint } from "$lib/shared/types/sprint";
  import type { TTableConfig } from "$lib/shared/types/table";
  import { sprintUtils } from "$lib/shared/utils/sprint";
  import LL from "$i18n/i18n-svelte";
  import { goto } from "$app/navigation";

  export let sprints: TSprint[];

  const config: TTableConfig<TSprint> = {
    columns: sprintUtils().getSprintTableColumns($LL),
    actions: [
      {
        label: "Enter",
        onClick: async (row) => {
          await goto(`/sprints/${btoa(row.id.toString())}`);
        },
      },
    ],
  };
</script>

<section class="space-y-4">
  <div class="flex items-center justify-between">
    <h2 class="text-lg font-semibold text-white">Sprints you’ve joined</h2>
  </div>

  <div class="rounded-xl border border-white/10 bg-white/5 p-4">
    <Table rows={sprints} {config} emptyText="No Sprints found" />
  </div>
</section>
