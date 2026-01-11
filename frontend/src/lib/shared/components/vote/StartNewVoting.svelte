<script lang="ts">
  import Button from "$components/design/Button.svelte";
  import Error from "$components/design/Error.svelte";
  import { getL18ErrorMessage } from "$lib/shared/api/http";
  import * as api from "$lib/shared/api/sprint";
  import type { TApiResponse, TSuccessResponse } from "$lib/shared/types/http";
  import type { TSprint } from "$lib/shared/types/sprint";
  import LL from "$i18n/i18n-svelte";

  export let sprintId: number;

  let response: TApiResponse<TSprint> | null = null;

  async function handleClick(sprintId: number) {
    response = await api.startNewVoting(sprintId);
  }
</script>

<div class="flex flex-col gap-4 items-center justify-center">
  <Button on:click={() => handleClick(sprintId)} fullWidth={false}>
    Start new voting
  </Button>

  <Error
    error={response && !response.success
      ? getL18ErrorMessage($LL.errors, response.result.error)
      : null}
  />
</div>
