import { redirect } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import { getSession } from '$lib/server/middleware/session';

/**
 * Root route always redirect to projects. The `session` middleware will handle
 * the access to it during the redirection.
 *
 * See: https://kit.svelte.dev/docs/load#page-data
 */
export const load = (async ({ locals: { t }, cookies }) => {
    if (getSession(cookies) === null) {
          throw redirect(302, t.routes.auth.logIn());
    } else {
        throw redirect(302, t.routes.user.homepage());
    }
}) satisfies PageServerLoad;
