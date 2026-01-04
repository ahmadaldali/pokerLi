import { redirect } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import { getSession } from '$lib/server/middleware/session';

/**
 * Root route always redirect to projects. The `session` middleware will handle
 * the access to it during the redirection.
 *
 * See: https://kit.svelte.dev/docs/load#page-data
 */
export const load = (async (event) => {
  console.log('Login page load called, checking session', event.locals.locale);
}) satisfies PageServerLoad;
