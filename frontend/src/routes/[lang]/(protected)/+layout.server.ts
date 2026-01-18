import type { LayoutServerLoad } from "./$types";

export const load = (async ({ parent }) => {
  const { user, locale, token } = await parent();

  return {
    locale,
    token,
    user,
  };
}) satisfies LayoutServerLoad;
