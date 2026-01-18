import type { Locales } from "$i18n/i18n-types";
import { tokenStore } from "$lib/shared/stores/user";
import type { TUser } from "$lib/shared/types/user";
import { redirect } from "@sveltejs/kit";
import type { LayoutServerLoad } from "./$types";
import { get } from "svelte/store";
import { usersApi } from "$lib/shared/api";
import { EUserRole } from "$lib/shared/enums/user";

/**
 * Root layout is responsible of returning global application data that can be
 * used everywhere across the application like the current {@link Locales}
 *
 * See: https://kit.svelte.dev/docs/load#layout-data
 */
export const load = (async (event ) => {
  const { user } = await event.parent();
  console.log("Admin layout load - user:", user);

  if (!user || user.role !== EUserRole.ADMIN) {
    throw redirect(302, "/");
  }
}) satisfies LayoutServerLoad;
