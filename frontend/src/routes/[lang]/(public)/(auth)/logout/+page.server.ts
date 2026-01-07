import { resetData } from '$lib/server/auth';
import { redirect, type RequestEvent } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import { getSession } from '$lib/server/middleware/session';

export const load: PageServerLoad = async (event: RequestEvent) => {
    const {
        locals: { t }
    } = event;

    if (getSession(event.cookies) !== null) {
        await resetData(event);
    }

    throw redirect(302, t.routes.auth.login());
};
