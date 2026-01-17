import type { LayoutServerLoad } from "./$types";
import { usersApi } from "$lib/shared/api";

export const load = (async ({}) => {
  const response = await usersApi().getUserSprints();

  return {
    userSprintsResponse: response,
    userSprints: response.result,
    userSprintsFetchedSuccessfully: response.success,
  };
}) satisfies LayoutServerLoad;
