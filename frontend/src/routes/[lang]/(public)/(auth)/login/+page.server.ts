import { redirect } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import { getSession } from '$lib/server/middleware/session';
import { api } from '$lib/shared/api/http';

/**
 * Root route always redirect to projects. The `session` middleware will handle
 * the access to it during the redirection.
 *
 * See: https://kit.svelte.dev/docs/load#page-data
 */
export const load = (async (event) => {
  console.log(event.locals.token)

}) satisfies PageServerLoad;
