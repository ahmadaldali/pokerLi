import { fail, redirect } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import * as api from '$lib/shared/api/index';
import { setSession } from '$lib/server/middleware/session.js';

/**
 * Root route always redirect to projects. The `session` middleware will handle
 * the access to it during the redirection.
 *
 * See: https://kit.svelte.dev/docs/load#page-data
 */
export const load = (async (event) => {
}) satisfies PageServerLoad;

export const actions = {
	default: async ({ request, cookies, fetch }) => {
		const form = await request.formData();
		const email = form.get('email');
		const password = form.get('password');

		const response = await api.login({ email, password }, fetch);

		if (response.success) {
			setSession(cookies, response.result.token);
			throw redirect(302, '/');
		}

		// Error case - works for both ORM errors and text
		console.log("ERROR:", response);
		return fail(400, { response });

	}
};