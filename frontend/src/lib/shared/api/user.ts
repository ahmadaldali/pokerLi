import { getR } from "./http";
import { PUBLIC_API_URL } from "$env/static/public";
import type { TUserSprintsApiResponse } from "../types/sprint";
import type { TApiResponse } from "../types/http";

const MODULE_ROUTE = "users";

type FetchFn = typeof fetch;

/**
 * Users API
 */
export const usersApi = () => {
  return {
    /**
     * Get current user
     */
    getUser: () => getR(`${PUBLIC_API_URL}/${MODULE_ROUTE}/me`),

    /**
     * Get user's sprints
     */
    getUserSprints: (
      fetchFn?: FetchFn
    ): Promise<TApiResponse<TUserSprintsApiResponse>> =>
      getR(`${PUBLIC_API_URL}/${MODULE_ROUTE}/sprints`, fetchFn),
  };
};
