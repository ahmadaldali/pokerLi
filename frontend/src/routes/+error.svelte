<script lang="ts">
    import { LL } from '$i18n/i18n-svelte';
    import { page } from '$app/state';
    import { PUBLIC_ENV } from '$env/static/public';

    import { goto } from '$app/navigation';
    import { browser } from '$app/environment';

    const status = page.status;
    const message = page.error?.message;
    const stack = (page.error as Error | undefined)?.stack ?? '';

    $: if (browser && PUBLIC_ENV !== 'localhost') {
        goto(redirectUser());
    }

    const redirectUser = () => {
        if (page.data.user?.role && status === 404) {
            return $LL.routes.user.homepage();
        } else {
            return $LL.routes.auth.logout();
        }
    };
</script>

<div class="error-container">
    <h1 style:margin-bottom="1rem">{status}: {message}</h1>
    {#if PUBLIC_ENV === 'localhost'}
        {stack}
    {/if}
    <a href={redirectUser()}>back</a>
</div>
