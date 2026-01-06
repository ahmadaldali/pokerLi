import type { RequestEvent } from "@sveltejs/kit";
import { deleteSession } from "./middleware/session";
import { tokenStore } from "$lib/shared/stores/user";

export const resetData = async (event: RequestEvent) => {
    const { cookies } = event;
    deleteSession(cookies);
    tokenStore.set(null);
    event!.locals!.user = null;
    event!.locals!.token = null;
};