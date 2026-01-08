import { sequence } from '@sveltejs/kit/hooks';
import { middlewareRunner } from './utils';
import session from './session';
import locale from './locale';
import redirect from './redirect';
import notFound from './not-found';

export const requestHandler = sequence(
    middlewareRunner('locale', locale),
    middlewareRunner('session', session),
    middlewareRunner('redirect', redirect),
    middlewareRunner('not-found', notFound),
);
