import type { PageServerLoad, Actions } from "./$types";
import * as api from "$lib/shared/api/user";

export const load: PageServerLoad = async ({ fetch }) => {
  return { apiResponse: await api.getUserSprints(fetch) };
};

export const actions: Actions = {
};
