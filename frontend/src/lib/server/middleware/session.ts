import { type RequestEvent, redirect, type Cookies } from "@sveltejs/kit";
import {
  isAsset,
  isAuthRoute,
  isProtectedRoute,
  isPublicRoute,
} from "$lib/shared/utils/check";
import type { MiddlewareBuilder } from "./utils";
import { PUBLIC_ENV } from "$env/static/public";
import type { Session } from "$lib/shared/types/session";
import { resetData } from "$lib/server/auth";

/** Implements the `session` middleware interface */
export default (({ logger, event, resolve }) => {
  return {
    canSkip() {
      return isAsset(event.route.id, event.url);
    },
    resolve() {
      return resolve(event);
    },
    async run() {
      return await Promise.resolve(hasSession()).then(checkSession).then((_) => resolve(event));
    },
  };

  /**
   * Checks the current request already contains a session in cookies. If not
   * it redirect to the `/login` page to let the user obtain one before
   * reaching its original requested page. Otherwise it returns the `session`.
   */
  async function hasSession(): Promise<any> {
    if (!event) {
      logger.info("Event object is required");
      throw redirect(303, "/");
    }

    const session = getSession(event.cookies);

    if (session) {
      return session;
    } else {
      logger.info("session not found", { event });
      await resetData(event);

      // If the accessed route is public set the session to undefined
      // in order to pass the event to the next method in squence
      if (isPublicRoute(event.route.id) || isAuthRoute(event.route.id)) {
        return undefined;
      }

      // If the accessed route is protected then redirect to login page
      throw redirect(303, await toLogIn(event));
    }
  }

  /**
   * Checks that specified `session` is still valid on the backend side. If
   * not valid, then deletes the session from the cookies and redirects to
   * the `/login` page.
   *
   * @param session
   * Session hash to validate.
   */
  async function checkSession(session: Session): Promise<any> {
    if (session === undefined) {
      return event;
    }

    try {
      // TODO : check if token is still valid

      return session;
    } catch (err) {
      logger.info("session expired", { err, event });
      await resetData(event);
      throw redirect(303, await toLogIn(event));
    }
  }

  /**
   *
   * Save the original requested url to redirect after login
   */
  async function toLogIn({ locals: { t } }: RequestEvent) {
    if (isProtectedRoute(event.route.id)) {
      setRedirectTo(event.cookies, event.url.pathname + event.url.search);
    }

    return `${t.routes.auth.logIn()}`;
  }
}) satisfies MiddlewareBuilder;

/**
 * Sets a user session across the application.
 *
 * @param authResult
 * The decoded hash you get after authentication.
 *
 * @param cookies
 * Request cookies object.
 */
export function setLocalSession(authResult: Session, cookies: Cookies) {
  const data = {
    token: authResult.token,
  };
  cookies.set(SESSION_KEY, JSON.stringify(data), {
    path: "/",
    ...(PUBLIC_ENV !== "localhost" && { domain: ".yoboo.health" }),
  });
}

/**
 * Get the session data from the cookies.
 *
 * @param cookies
 * Request cookies object.
 */
export function getSession(cookies: Cookies): Session | null {
  try {
    const encryptedData = cookies.get(SESSION_KEY);
    if (!encryptedData) return null;
    return JSON.parse(encryptedData);
  } catch (error) {
    console.error("Error decrypting session data:", error);
    return null;
  }
}

/**
 * Verify if the session expired.
 * If verifySession returns `true`: The session is still valid, and the user is considered authenticated.
 * If verifySession returns `false`: The session is either missing or has expired, and the user is considered not authenticated.
 *
 * @param cookies
 * Request cookies object.
 */
export function verifySession(expiredTime: number): boolean {
  if (!expiredTime) return false;

  const now = new Date().getTime();
  return now < expiredTime;
}

/**
 * Deletes a user session.
 *
 * @param cookies
 * Request cookies object.
 */
export function deleteSession(cookies: Cookies) {
  cookies.delete(SESSION_KEY, {
    path: "/",
    ...(PUBLIC_ENV !== "localhost" && { domain: ".yoboo.health" }),
  });
}

/** Obtain the key used user session key in request cookies. */
const SESSION_KEY = `sessionid_${PUBLIC_ENV}`;

/**
 * Obtain the key used a query string parameter to redirect to original page
 * requested by user before landing into the sign-in page.
 */
const REDIRECT_KEY = "redirectTo";

export function setRedirectTo(cookies: Cookies, url: string) {
  cookies.set(REDIRECT_KEY, url, { path: "/" });
}

export function getRedirectTo(cookies: Cookies) {
  return cookies.get(REDIRECT_KEY);
}

export function deleteRedirectTo(cookies: Cookies) {
  cookies.delete(REDIRECT_KEY, { path: "/" });
}
