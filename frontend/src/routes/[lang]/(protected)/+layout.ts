import { tokenStore } from "$lib/shared/stores/user";
import type { LayoutLoad } from "./$types";

export const load: LayoutLoad = async (event) => {
  if (event.data) {
        const { token } = event.data;
      if (token) tokenStore.set(token);
  }

  return {};
};