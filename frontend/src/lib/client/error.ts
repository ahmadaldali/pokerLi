import type { HandleClientError } from '@sveltejs/kit';
import { logger } from '$lib/shared/services';

export const errorHandler = (({ error }) => {
    const e = error as Error;

    logger.error(e.message, { error: e });
    return {
        message: e.message
    };
}) satisfies HandleClientError;
