import { building } from '$app/environment';
import { errorHandler } from '$lib/server/error';
import { init } from '$lib/server/init';
import { requestHandler } from '$lib/server/middleware';

// Initializes client app
if (!building) {
    init();
}

// This function runs every time the SvelteKit server receives a request
// See https://kit.svelte.dev/docs/hooks#server-hooks

export async function handle({ event, resolve }) {
    // console.log('HOOKS: incoming', event.request.method, event.url.href);
    const res = await requestHandler({ event, resolve });

    // 502 Bad Gateway
    res.headers.delete('Link');
    res.headers.delete('link');

    return res;
}

// This function runs every time the SvelteKit server throw an error
// See https://kit.svelte.dev/docs/hooks#shared-hooks-handleerror
export const handleError = errorHandler;
