<script lang="ts">
  import { LL } from '$i18n/i18n-svelte'
  import { locale } from '$i18n/i18n-svelte';
</script>

locale is {$locale}
<h1>{$LL.auth.login()}</h1>
