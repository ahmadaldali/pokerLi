import type { TRawData } from "../types/general";
import { api } from "./http";
import { PUBLIC_API_URL } from "$env/static/public";

const MODULE_ROUTE = "auth";

export const register = (data: TRawData, fetchFn?: typeof fetch) => {
  return Promise.resolve(
    api({
      url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/register`,
      method: "POST",
      fetch: fetchFn,
      data: data,
    })
  );
};

export const login = (data: TRawData, fetchFn?: typeof fetch) => {
  return Promise.resolve(
    api({
      url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/login`,
      method: "POST",
      fetch: fetchFn,
      data: data,
    })
  );
};

export const createGuest = (
  data: {
    name: string;
    guestId: string;
  },
  fetchFn?: typeof fetch
) => {
  return Promise.resolve(
    api({
      url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/guest`,
      method: "POST",
      fetch: fetchFn,
      data: data,
    })
  );
};
