import { tokenStore } from "$lib/shared/stores/user";
import type { TCurrentLoggedInUser } from "$lib/shared/types/user";
import type { LayoutLoad } from "./$types";

export const load: LayoutLoad = async (event) => {
  const { token } = event.data;
  tokenStore.set(token);
  
  return {
    user: event.data.user as TCurrentLoggedInUser,
  };
};
