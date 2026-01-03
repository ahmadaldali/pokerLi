import { sequence } from '@sveltejs/kit/hooks';
// import notFound from './not-found';
import { middlewareRunner } from './utils';
import session from './session';
import locale from './locale';
import redirect from './redirect';

export const requestHandler = sequence(
    middlewareRunner('locale', locale),
    middlewareRunner('session', session),
    middlewareRunner('redirect', redirect)
);
