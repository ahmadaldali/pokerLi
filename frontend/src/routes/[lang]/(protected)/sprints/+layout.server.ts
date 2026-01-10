import type { LayoutServerLoad } from "./$types";
import * as api from "$lib/shared/api/user";
import type { TUserSprintsApiResponse } from "$lib/shared/types/sprint";

export const load = (async ({}) => {
  const response = await api.getUserSprints();

  return {
    userSprintsResponse: response,
    userSprints: response.result,
    userSprintsFetchedSuccessfully: response.success,
  };
}) satisfies LayoutServerLoad;
