import { tokenStore } from "$lib/shared/stores/user";
import type { LayoutLoad } from "./$types";

export const load: LayoutLoad = async (event) => {
  const { token } = await event.parent();
  tokenStore.set(token); // set the store token "client side"
};
