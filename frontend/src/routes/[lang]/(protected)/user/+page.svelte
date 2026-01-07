<script lang="ts">
  import { getSprint } from "$lib/shared/api/sprint";
  import { onMount } from "svelte";
  import type { PageData } from "./$types";
  import PokerLiLogo from "$components/design/PokerLiLogo.svelte";

  export let data: PageData;

  console.log("data in page:", data);

  const user = data.user;
  

  onMount(async () => {
    await getSprint("2").then((response) => {
      if (response.success) {
        console.log("Test api:", response.result);
      } else {
        console.warn("Failed to fetch user data:", response.result);
      }
    });
  });

  let userMenuOpen = false;

  let mobileOpen = false;

  </script>
