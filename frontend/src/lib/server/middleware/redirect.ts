import { redirect } from '@sveltejs/kit';
import type { MiddlewareBuilder } from './utils';
import { isAsset, isKeyOfObject, isPublicRoute } from '$lib/shared/utils/check';

export default (({ logger, event, resolve }) => {
    return {
        canSkip() {
            return isAsset(event.route.id, event.url);
        },
        resolve() {
            return resolve(event);
        },
        async run() {
            if (event.url.host.startsWith('www.')) {
                throw redirect(302, event.url.href.replace('www.', ''));
            }
            if (isPublicRoute(event.route.id)) {
                const language = event.url.pathname.split('/')[1];
                const urlToCheck = event.url.pathname.split('/').slice(2).join('/');

                if (isKeyOfObject(urlsToRedirect, urlToCheck)) {
                    throw redirect(302, `${event.url.protocol}//${event.url.host}/${language}/${urlsToRedirect[urlToCheck]}`);
                }
            }
            return resolve(event);
        }
    };
}) satisfies MiddlewareBuilder;

const urlsToRedirect = {
    signup: 'sign-up'
} as const;
