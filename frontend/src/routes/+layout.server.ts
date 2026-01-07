import type { Locales } from "$i18n/i18n-types";
import { getUser } from "$lib/shared/api/user";
import { tokenStore } from "$lib/shared/stores/user";
import type { TCurrentLoggedInUser } from "$lib/shared/types/user";
import type { LayoutServerLoad } from "./$types";

/**
 * Root layout is responsible of returning global application data that can be
 * used everywhere across the application like the current {@link Locales}
 *
 * See: https://kit.svelte.dev/docs/load#layout-data
 */
export const load = (async ({ locals, params }) => {
  let user = null as TCurrentLoggedInUser | null;

  if (locals.token) {
    // Set the token and user for usage in the client side
    tokenStore.set(locals.token); // save the token in the server store
    user = (await getUser()).result;
  }

  return {
    locale: (params.lang as Locales | undefined) ?? ("en" as Locales),
    token: locals.token,
    user,
  };
}) satisfies LayoutServerLoad;
