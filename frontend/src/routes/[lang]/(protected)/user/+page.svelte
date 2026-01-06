<script lang="ts">
  import { api } from "$lib/shared/api/http";
  import { getSprint } from "$lib/shared/api/sprint";
  import { getUser } from "$lib/shared/api/user";
  import { onMount } from "svelte";
  import type { PageData } from "./$types";

  export let data: PageData;

  console.log("data in page:", data);
  

  onMount(async () => {
    await getSprint("2").then((response) => {
      if (response.success) {
        console.log("Test api:", response.result);
      } else {
        console.warn("Failed to fetch user data:", response.result);
      }
    });
  });

  </script>