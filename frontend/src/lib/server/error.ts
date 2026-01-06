import type { HandleServerError } from '@sveltejs/kit';
import { logger } from '$lib/shared/services';

/** Error handler function called for all errors thrown by the server */
export const errorHandler = ((event) => {
    const e = event.error as Error;

    logger.error(e.message, event);

    return {
        message: e.message,
        stack: ((e?.cause as Error) ?? e)?.stack as string
    };
}) satisfies HandleServerError;
