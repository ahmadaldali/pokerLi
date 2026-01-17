<script lang="ts">
  import Error from "$components/design/Error.svelte";
  import { getL18ErrorMessage } from "$lib/shared/api/http";
  import type { PageData } from "./$types";
  import LL from "$i18n/i18n-svelte";
  import JoinableSprintsTable from "$components/sprint/JoinableSprintsTable.svelte";
  import JoinedSprintsTable from "$components/sprint/JoinedSprintsTable.svelte";

  export let data: PageData;
</script>

{#if data.userSprintsFetchedSuccessfully}
  <div class="flex flex-col gap-10">
    <JoinedSprintsTable sprints={data.userSprints.joined} />
    <JoinableSprintsTable sprints={data.userSprints.joinable} />
  </div>
{:else}
  <Error
    error={getL18ErrorMessage(
      $LL.errors,
      data.userSprintsResponse.result.error
    )}
  />
{/if}
