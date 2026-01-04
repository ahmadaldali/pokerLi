import type { RequiredParams } from 'typesafe-i18n';
import type { Locales } from './i18n-types';

export function localeRoutes(locale: Locales) {
    return {
        href: `/${locale}/{0}` as RequiredParams<'0'>,
        auth: {
            logIn: `/${locale}/login`,
            signUp: `/${locale}/sign-up`,
            logOut: `/${locale}/logout`
        },
        user: {
            homepage: `/${locale}/`,
        }
    };
}
