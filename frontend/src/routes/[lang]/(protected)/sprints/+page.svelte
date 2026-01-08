<script lang="ts">
  import Error from "$components/design/Error.svelte";
  import Table from "$components/design/Table.svelte";
  import { getL18ErrorMessage } from "$lib/shared/api/http";
  import type { TableConfig } from "$lib/shared/types/table";
  import { generateRandomString } from "$lib/shared/utils/helper";
  import type { PageData } from "./$types";
  import LL from "$i18n/i18n-svelte";

  export let data: PageData;

  console.log(data);

  const tableConfig: TableConfig<any> = {
    columns: [
      { key: "name", label: "User Story" },
      { key: "creator", label: "Room" },
    ],
    actions: [
      {
        label: "Open",
        href: (row) => `/rooms/${generateRandomString(row.id)}`,
      },
    ],
  };
</script>

{#if data.apiResponse.success}
  <Table
    rows={data.apiResponse.result.joinable}
    config={tableConfig}
    emptyText="No rooms found"
  />
{:else}
  <Error
    error={getL18ErrorMessage($LL.errors, data.apiResponse.result.error)}
  />
{/if}
