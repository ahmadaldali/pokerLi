import { redirect } from "@sveltejs/kit";
import { getSession } from "$lib/server/middleware/session";

/**
 * Root route always redirect to projects. The `session` middleware will handle
 * the access to it during the redirection.
 *
 * See: https://kit.svelte.dev/docs/load#page-data
 */
export const load = (async ({ locals: { t }, cookies }) => {
  if (getSession(cookies) !== null) {
    // If the user is logged in redirect to home page
    throw redirect(302, t.routes.user.homepage());
  }

  // Otherwise redirect to login page
   throw redirect(302, t.routes.auth.logIn());
})
