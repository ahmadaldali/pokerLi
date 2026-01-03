import { building } from '$app/environment';
import { errorHandler } from '$lib/client/error';
import { init } from '$lib/client/init';

// Initializes client app
if (!building) {
    init();
}

// This function runs every time the SvelteKit server throw an error
// See https://kit.svelte.dev/docs/hooks#shared-hooks-handleerror
export const handleError = errorHandler;
