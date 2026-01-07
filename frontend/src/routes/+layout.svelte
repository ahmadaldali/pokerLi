<script lang="ts">
  import { setLocale } from '$i18n/i18n-svelte';
  import { beforeNavigate } from '$app/navigation';
  import { page, updated } from '$app/state';
  import favicon from '$lib/assets/favicon.svg';
  import type { Locales } from '$i18n/i18n-types';
  import '../app.css';
  import Header from '$components/layout/Header.svelte';

  $effect(() => {
    const lang = (page?.params?.lang ?? 'en') as Locales;
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

<Header user={page.data.user} />

<div
  class="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-950 via-slate-900 to-emerald-950"
>
  <div
    class="w-full max-w-md rounded-2xl bg-slate-900/80 backdrop-blur border border-white/10 shadow-2xl p-8"
  >
{@render children()}
  </div>
</div>
