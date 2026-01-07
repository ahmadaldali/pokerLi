import type { PageServerLoad, Actions } from "./$types";
import * as api from "$lib/shared/api/sprint";

export const load: PageServerLoad = async ({ locals, fetch, params }) => {
 const apiResponse = await api.getSprint(params.id, fetch);

 console.log(apiResponse);

  return { apiResponse };
};

export const actions: Actions = {
};
