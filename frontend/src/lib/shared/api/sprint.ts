import { post } from "./http";
import { PUBLIC_API_URL } from "$env/static/public";
import type { TSprint } from "$lib/shared/types/sprint";
import type { TApiResponse } from "$lib/shared/types/http";

const MODULE_ROUTE = "sprints";

/**
 * Sprints API
 */
export const sprintsApi = () => {
  return {
    /**
     * Create a new sprint
     */
    create: (
      data: { name: string; sequence: number[] },
      fetchFn?: typeof fetch,
    ): Promise<TApiResponse<TSprint>> =>
      post(`${PUBLIC_API_URL}/${MODULE_ROUTE}`, fetchFn, data),

    /**
     * Start a new voting round
     */
    startNewVoting: (
      sprintId: number,
      fetchFn?: typeof fetch,
    ): Promise<TApiResponse<TSprint>> =>
      post(
        `${PUBLIC_API_URL}/${MODULE_ROUTE}/${sprintId}/start-new-voting`,
        fetchFn,
      ),

    /**
     * Join sprint
     */
    join: (
      sprintId: number,
      fetchFn?: typeof fetch,
    ): Promise<TApiResponse<TSprint>> =>
      post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/${sprintId}/join`, fetchFn),
  };
};
