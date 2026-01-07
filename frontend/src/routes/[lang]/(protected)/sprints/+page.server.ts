import type { PageServerLoad, Actions } from "./$types";
import * as api from "$lib/shared/api/user";

export const load: PageServerLoad = async ({ locals, fetch }) => {
 const apiResponse = await api.getUserSprints(fetch);

  return { apiResponse };
};

export const actions: Actions = {
};
