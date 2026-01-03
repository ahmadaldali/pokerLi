import { initAcceptLanguageHeaderDetector } from 'typesafe-i18n/detectors';
import { redirect } from '@sveltejs/kit';
import type { MiddlewareBuilder } from './utils';
import { isAsset } from '$lib/shared/utils/check';
import { detectLocale, i18n, isLocale } from '$i18n/i18n-util';
import type { Locales } from '$i18n/i18n-types';
import { setLocale } from '$i18n/i18n-svelte';


export default (({ event, resolve }) => {
    return {
        canSkip() {
            return isAsset(event.route.id, event.url);
        },
        resolve() {
            return resolve(event);
        },
        async run() {
            return await Promise.resolve(hasLanguageSlug())
                .then(localeFromSlug)
                .then(bindLocale)
                .then((locale) =>
                    resolve(event, {
                        transformPageChunk: ({ html }) => html.replace('%lang%', locale ?? 'en')
                    })
                );
        }
    };

    /**
     * Checks for the `lang` slug being specified otherwise check for preferred
     * locale.
     *
     * NOTE:
     * Can probably be skipped with the usage of `locale` param matcher. Need to
     * be tested before dropping this step.
     */
    function hasLanguageSlug() {
        // logger.info('read language slug', {event});
        const [, lang] = event.url.pathname.split('/');

        if (lang === undefined) {
            // logger.info('no locale slug, redirect to base locale route', {event});
            throw redirect(302, toLocalizedRoute());
        } else {
            return lang;
        }
    }

    /**
     * Converts the language `slug` into a valid {@link Locales}. If `slug` is
     * not a valid {@link Locales} then it uses the language obtain from the
     * {@link getPreferredLocale} methods.
     */
    function localeFromSlug(slug: string): Locales {
        // logger.info('check language slug is valid locale');

        if (isLocale(slug)) {
            // logger.info('language slug is a valid locale', {slug});
            return slug as Locales;
        } else {
            // logger.info('language slug is not a valid locale', {slug});
            throw redirect(302, toLocalizedRoute());
        }
    }

    /**
     * Bind the current `locale` to the request event `locals` to let other
     * middlewares and server handlers being able to access it.
     *
     * See: https://kit.svelte.dev/docs/types#app-locals
     */
    function bindLocale(locale: Locales): Locales {
        // logger.info('bind locale to current request');
        event.locals.locale = locale;
        event.locals.t = L[locale];

        console.log('Setting locale in middleware to', event.locals.locale);

        if (event.locals.locale) {
            console.log('Setting locale in i18n-svelte to', event.locals.locale);
            setLocale(event.locals.locale);
        } else {
                console.log('Setting default locale in i18n-svelte to', event.locals.locale);
            setLocale('en');
        }

        console.log('Current locale in i18n-svelte is', locale);

        return locale || 'en';
    }

    /**
     * Detect the preferred language the user has configured in his browser.
     *
     * See: https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Language
     */
    function getPreferredLocale(): Locales {
        // logger.info('read preferred locale');
        const detector = initAcceptLanguageHeaderDetector(event.request);

        return detectLocale(detector);
    }

    function toLocalizedRoute() {
        const locale = getPreferredLocale();

        return `/${locale}${event.url.pathname}${event.url.search}`;
    }
}) satisfies MiddlewareBuilder;

/** Gets translations functions. */
const L = i18n();
