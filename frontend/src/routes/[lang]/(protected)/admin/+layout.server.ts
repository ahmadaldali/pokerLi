import { redirect } from "@sveltejs/kit";
import type { LayoutServerLoad } from "./$types";
import { EUserRole } from "$lib/shared/enums/user";

export const load = (async (event) => {
  const parentData = await event.parent();
  const { user } = parentData;
  
  if (!user || user.role !== EUserRole.ADMIN) {
    throw redirect(302, "/");
  }

  return {
    ...parentData,
  };
}) satisfies LayoutServerLoad;
