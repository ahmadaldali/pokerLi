import { getUser } from '$lib/shared/api/user.js';
import { tokenStore } from '$lib/shared/stores/user.js';
import { redirect } from '@sveltejs/kit';
import type { LayoutServerLoad } from './$types.js';
import { redirectTo } from "$lib/shared/utils/redirect";

export const load: LayoutServerLoad = async ({ locals }) => {

  // Set the token in the store or redirect to logout if not present
  if (locals.token) {
    tokenStore.set(locals.token);
  } else {
    redirectTo(locals.t.routes.auth.logOut());
  }

  return {
    user: (await getUser()).result,
    token: locals.token
  };
};
