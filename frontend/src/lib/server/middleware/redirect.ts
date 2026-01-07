import { redirect } from "@sveltejs/kit";
import type { MiddlewareBuilder } from "./utils";
import {
  isAsset,
  isKeyOfObject,
  isLogoutRoute,
  isPublicRoute,
} from "$lib/shared/utils/check";
import { getSession } from "./session";

export default (({ logger, event, resolve }) => {
  return {
    canSkip() {
      return isAsset(event.route.id, event.url);
    },
    resolve() {
      return resolve(event);
    },
    async run() {
      const language = event.url.pathname.split("/")[1];

      if (event.url.host.startsWith("www.")) {
        throw redirect(302, event.url.href.replace("www.", ""));
      }

      if (isPublicRoute(event.route.id)) {
        const urlToCheck = event.url.pathname.split("/").slice(2).join("/");

        if (isKeyOfObject(urlsToRedirect, urlToCheck)) {
          throw redirect(
            302,
            `${event.url.protocol}//${event.url.host}/${language}/${urlsToRedirect[urlToCheck]}`
          );
        }
        
        if (getSession(event.cookies) && !isLogoutRoute(event.route.id)) {
          // If the user is already logged in redirect to home page
          throw redirect(
            302,
           event.locals.t.routes.user.homepage()
          );
        }
      }

      if (!isPublicRoute(event.route.id) && !getSession(event.cookies)) {
        // If the user is not logged in redirect to login page
        throw redirect(
          302,
          event.locals.t.routes.auth.login()
        );
      }

      return resolve(event);
    },
  };
}) satisfies MiddlewareBuilder;

const urlsToRedirect = {
  signup: "sign-up",
} as const;
