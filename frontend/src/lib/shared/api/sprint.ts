import { post } from "./http";
import { PUBLIC_API_URL } from "$env/static/public";
import type { TSprint } from "../types/sprint";
import type { TApiResponse } from "../types/http";

const MODULE_ROUTE = "sprints";

/**
 * Sprints API
 */
export const sprintsApi = () => {
  return {
    /**
     * Start a new voting round
     */
    startNewVoting: (sprintId: number, fetchFn?: typeof fetch): Promise<TApiResponse<TSprint>> =>
      post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/${sprintId}/start-new-voting`, fetchFn),

    /**
     * Join sprint
     */
    join: (sprintId: number, fetchFn?: typeof fetch): Promise<TApiResponse<TSprint>> =>
      post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/${sprintId}/join`, fetchFn),
  };
};
