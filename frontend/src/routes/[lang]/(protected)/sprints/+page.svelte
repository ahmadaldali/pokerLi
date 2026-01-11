<script lang="ts">
  import Error from "$components/design/Error.svelte";
  import Table from "$components/design/Table.svelte";
  import { getL18ErrorMessage } from "$lib/shared/api/http";
  import type { TTableConfig } from "$lib/shared/types/table";
  import { generateRandomString } from "$lib/shared/utils/helper";
  import type { PageData } from "./$types";
  import LL from "$i18n/i18n-svelte";

  export let data: PageData;

  const tableConfig: TTableConfig<any> = {
    columns: [
      { key: "name", label: "Room" },
      { key: "creator", label: "Creator" },
    ],
    actions: [
      {
        label: "Open",
        href: (row) => `/rooms/${generateRandomString(row.id)}`,
      },
    ],
  };
</script>

{#if data.userSprintsFetchedSuccessfully}
  <Table
    rows={data.userSprints.joinable}
    config={tableConfig}
    emptyText="No rooms found"
  />
{:else}
  <Error
    error={getL18ErrorMessage($LL.errors, data.userSprintsResponse.result.error)}
  />
{/if}
