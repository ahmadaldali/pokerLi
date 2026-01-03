import type { RequestEvent } from "@sveltejs/kit";
import { deleteSession } from "./middleware/session";

export const resetData = async (event: RequestEvent) => {
    const { cookies } = event;
    deleteSession(cookies);
    event!.locals!.user = null;
};