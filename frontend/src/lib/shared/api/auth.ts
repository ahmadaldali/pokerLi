import { post } from "./http";
import { PUBLIC_API_URL } from "$env/static/public";

const MODULE_ROUTE = "auth";

type FetchFn = typeof fetch;

/**
 * Auth API
 */
export const authApi = () => {
  return {
    /**
     * Register user
     */
    register: (
      data: {
        name: string;
        email: string;
        password: string;
        refCode?: string;
      },
      fetchFn?: FetchFn
    ) => post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/register`, fetchFn, data),

    /**
     * Login user
     */
    login: (
      data: {
        email: string;
        password: string;
      },
      fetchFn?: FetchFn
    ) => post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/login`, fetchFn, data),

    /**
     * Create guest user
     */
    createGuest: (
      data: {
        name: string;
        guestId: string;
      },
      fetchFn?: FetchFn
    ) => post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/guest`, fetchFn, data),
  };
};
