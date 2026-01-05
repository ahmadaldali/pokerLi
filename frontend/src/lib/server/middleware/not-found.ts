import { redirect } from '@sveltejs/kit';
import type { MiddlewareBuilder } from './utils';

/** Implements the `not-found` middleware interface */
export default (({ logger, event, resolve }) => ({
    canSkip() {
        return event.route.id !== null;
    },
    resolve() {
        return resolve(event);
    },
    run() {
        if (event.url.pathname.includes('/.well-known/')) {
            return new Response(null);
        } else {
            logger.warn('Page not found', { event });
            throw redirect(302, event.locals.t.routes.user.homepage());
        }
    }
})) satisfies MiddlewareBuilder;
