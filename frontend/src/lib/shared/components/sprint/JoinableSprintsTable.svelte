<script lang="ts">
  import { goto } from "$app/navigation";
  import Error from "$components/design/Error.svelte";
  import Table from "$components/design/Table.svelte";
  import LL from "$i18n/i18n-svelte";
  import { getL18ErrorMessage, join } from "$lib/shared/api";
  import type { TApiResponse } from "$lib/shared/types/http";
  import type { TSprint } from "$lib/shared/types/sprint";
  import type { TTableConfig } from "$lib/shared/types/table";
  import { sprintUtils } from "$lib/shared/utils/sprint";

  export let sprints: TSprint[];

  const config: TTableConfig<TSprint> = {
    columns: sprintUtils().getSprintTableColumns($LL),
    actions: [
      {
        label: "Join",
        onClick: async (row) => {
          responseForJoin = await join(row.id);
          if (responseForJoin.success) {
            await goto(`/sprints/${btoa(row.id.toString())}`);
          }
        },
      },
    ],
  };

  let responseForJoin = undefined as TApiResponse | undefined;
</script>

<section class="space-y-4">
  <div class="flex items-center justify-between">
    <h2 class="text-lg font-semibold text-white">Sprints you can join</h2>
  </div>

  <div class="rounded-xl border border-white/10 bg-white/5 p-4">
    <Table
      rows={sprints}
      {config}
      emptyText="No Sprints found"
    />
  </div>
  <Error
    error={getL18ErrorMessage($LL.errors, responseForJoin?.result?.error)}
  />
</section>
