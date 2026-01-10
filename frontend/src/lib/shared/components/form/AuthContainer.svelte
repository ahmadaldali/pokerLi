<script lang="ts">
  import Error from "$components/design/Error.svelte";
  import { getL18ErrorMessage } from "$lib/shared/api/http";
  import LL from "$i18n/i18n-svelte";
  import type { TApiResponse } from "$lib/shared/types/http";
  import Link from "$components/design/Link.svelte";
  import PokerLiLogo from "$components/design/PokerLiLogo.svelte";

  export let title: string;

  export let response: TApiResponse | undefined;
  export let link: string | null = null;
  export let linkText: string = "";
  export let linkTitle: string = "";
</script>

<!-- Logo / Title -->
<div class="text-center mb-8">
 <PokerLiLogo />
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
  <Link link={link} linkText={linkText} linkTitle={linkTitle} />
{/if}

<!-- Footer -->
<p class="mt-6 text-center text-xs text-slate-500">
  © {new Date().getFullYear()} pokerLi · Play smart ♠
</p>
