import type { HttpError, Redirect } from '@sveltejs/kit';

export function isRedirect(e: any): e is Redirect {
    if (e == null) {
        return false;
    } else {
        return typeof e.status === 'number' && typeof e.location === 'string';
    }
}

export function isHttpError(e: any): e is HttpError {
    if (e == null) {
        return false;
    } else {
        return typeof e.status === 'number' && typeof e?.body.message === 'string';
    }
}

export function isAsset(routeId: string | null, url: URL) {
    return routeId === null && (url.pathname.startsWith('/@fs'));
}

export function isLocalizedRoute(routeId: string | null) {
    return routeId !== null && routeId.startsWith('/[lang]');
}

export function isAuthRoute(routeId: string | null) {
    return routeId !== null && routeId.startsWith('/[lang]/(public)/(auth)');
}

export function isSignUpRoute(routeId: string | null) {
    return routeId !== null && routeId.startsWith('/[lang]/(public)/(auth)/sign-up');
}

export function isPublicRoute(routeId: string | null) {
    return routeId !== null && routeId.startsWith('/[lang]/(public)');
}

export function isProtectedRoute(routeId: string | null) {
    return routeId !== null && routeId.startsWith('/[lang]/(protected)');
}

export function isUserRoute(routeId: string | null) {
    return routeId !== null && routeId.startsWith('/[lang]/(protected)/user');
}

export function isEmpty(obj: object): boolean {
    return Object.keys(obj).length === 0;
}

export function isKeyOfObject<T extends Record<string, unknown>>(obj: T, key: string | number | symbol): key is keyof T {
    return key in obj;
}
