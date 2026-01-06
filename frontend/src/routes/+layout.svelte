<script lang="ts">
  import { setLocale } from '$i18n/i18n-svelte';
  import { beforeNavigate } from '$app/navigation';
  import { page, updated } from '$app/stores';
  import favicon from '$lib/assets/favicon.svg';
  import type { Locales } from '$i18n/i18n-types';
  import '../app.css';

  $effect(() => {
    const lang = ($page?.params?.lang ?? 'en') as Locales;
    if (lang) {
      setLocale(lang);
    }
  });

  beforeNavigate(({ willUnload, to }) => {
    if (updated && !willUnload && to?.url) {
      location.href = to.url.href;
    }
  });

  let { children } = $props();
</script>

<svelte:head>
  <link rel="icon" href={favicon} />
</svelte:head>

{@render children()}
