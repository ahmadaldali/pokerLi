import type { RequiredParams } from 'typesafe-i18n';
import type { Locales } from './i18n-types';

export function localeRoutes(locale: Locales) {
    return {
        href: `/${locale}/{0}` as RequiredParams<'0'>,
        auth: {
            login: `/${locale}/login`,
            signUp: `/${locale}/sign-up`,
            logout: `/${locale}/logout`
        },
        user: {
            homepage: `/${locale}/user`,
        },
        sprints: {
            index: `/${locale}/sprints`,
            details: `/${locale}/sprints/{0}` as RequiredParams<'0'>,
        }
    };
}
