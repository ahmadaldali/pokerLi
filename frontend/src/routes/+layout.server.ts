import type { Locales } from "$i18n/i18n-types";
import { tokenStore } from "$lib/shared/stores/user";
import type { TUser } from "$lib/shared/types/user";
import { redirect } from "@sveltejs/kit";
import type { LayoutServerLoad } from "./$types";
import { get } from "svelte/store";
import { usersApi } from "$lib/shared/api";

/**
 * Root layout is responsible of returning global application data that can be
 * used everywhere across the application like the current {@link Locales}
 *
 * See: https://kit.svelte.dev/docs/load#layout-data
 */
export const load = (async ({ locals, params }) => {
  let user = null as TUser | null;

  const token = get(tokenStore);
  if (token) {
    const apiResponse = await usersApi().getUser();

    if (!apiResponse.success) {
      // expired or invalid token, force logout
      throw redirect(302, locals.t.routes.auth.logout());
    }

    // Set the token and user for usage in the client side
    user = apiResponse.result as TUser;
  }

  return {
    locale: (params.lang as Locales | undefined) ?? ("en" as Locales),
    token: token,
    user,
  };
}) satisfies LayoutServerLoad;
