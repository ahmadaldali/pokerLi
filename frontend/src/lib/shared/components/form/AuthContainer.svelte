<script lang="ts">
  import Error from "$components/design/Error.svelte";
  import { getL18ErrorMessage } from "$lib/shared/api/http";
  import LL from "$i18n/i18n-svelte";
  import type { Writable } from "svelte/store";
  import type { ApiResponse } from "$lib/shared/types/http";

  export let title: string;

  export let response: ApiResponse | undefined;
  export let link: string | null = null;
  export let linkText: string = "";
  export let linkTitle: string = "";
</script>

<!-- Logo / Title -->
<div class="text-center mb-8">
  <h1 class="text-3xl font-bold tracking-tight text-white">
    poker<span class="text-emerald-400">Li</span>
  </h1>
  <p class="mt-2 text-sm text-slate-400">{title}</p>
</div>

<!-- API Error -->
{#if response && !response.success}
  <Error
    class="mb-6"
    error={getL18ErrorMessage($LL.errors, response?.result?.error)}
  />
{/if}

<slot />

<!-- Link -->
{#if link}
  <p class="mt-6 text-center text-sm text-slate-400">
    {linkTitle}
    <a
      href={link}
      class="ml-1 font-medium text-emerald-400 hover:text-emerald-300 underline-offset-4 hover:underline transition"
    >
      {linkText}
    </a>
  </p>
{/if}

<!-- Footer -->
<p class="mt-6 text-center text-xs text-slate-500">
  © {new Date().getFullYear()} pokerLi · Play smart ♠
</p>
