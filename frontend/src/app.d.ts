// See https://svelte.dev/docs/kit/types#app.d.ts

import type { Locales, TranslationFunctions } from '$i18n/i18n-types';
import type { Session as LocalSession } from '$lib/server/middleware/session';
import type { TApiResponse } from '$lib/shared/types/http';
import type { TCurrentLoggedInUser } from '$lib/shared/types/user';

// for information about these interfaces
declare global {
    namespace App {
        // interface Error {}
        // interface Locals {}
        // interface PageData {}
        // interface PageState {}
        // interface Platform {}
		interface Locals {
            user: TCurrentLoggedInUser | null;
            t: TranslationFunctions;
            redirectTo: string | null;
            locale: Locales;
            token: string | null;
        }
        namespace Superforms {
        type Message = TApiResponse;
    }
    }
}

export {};
