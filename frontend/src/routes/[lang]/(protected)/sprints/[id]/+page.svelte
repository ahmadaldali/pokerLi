<script lang="ts">
  import type { PageData } from "./$types";


    export let data: PageData

    const { apiResponse } = data;
</script>


{#if apiResponse.success}
  <h1>Sprint Details</h1>
  <pre>{JSON.stringify(apiResponse.result, null, 2)}</pre>
{:else}
  <h1>Error Loading Sprint</h1>
  <p>There was an error loading the sprint details.</p>
  <pre>{JSON.stringify(apiResponse.result, null, 2)}</pre>
{/if}
