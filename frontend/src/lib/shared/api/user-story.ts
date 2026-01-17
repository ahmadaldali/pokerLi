import { api } from "./http";
import { PUBLIC_API_URL } from "$env/static/public";
import type { TApiResponse, TSuccessResponse } from "$lib/shared/types/http";
import type { TRawData } from "../types/general";

const MODULE_ROUTE = "user-stories";

type FetchFn = typeof fetch;

type VotePayload = {
  estimation: number;
};

function post<T = TSuccessResponse>(
  path: string,
  fetchFn?: FetchFn,
  data?: TRawData,
): Promise<TApiResponse<T>> {
  return api({
    url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/${path}`,
    method: "POST",
    fetch: fetchFn,
    data,
  });
}

/**
 * User Stories API
 */
export const userStoryApi = () => {
  return {
    /**
     * Vote on a user story
     */
    vote: (
      userStoryId: number,
      data: VotePayload,
      fetchFn?: FetchFn
    ) =>
      post(`${userStoryId}/vote`, fetchFn, data),

    /**
     * Remove current vote
     */
    unVote: (userStoryId: number, fetchFn?: FetchFn) =>
      post(`${userStoryId}/un-vote`, fetchFn),

    /**
     * Vote again after reveal
     */
    voteAgain: (userStoryId: number, fetchFn?: FetchFn) =>
      post(`${userStoryId}/vote-again`, fetchFn),

    /**
     * Reveal votes
     */
    reveal: (userStoryId: number, fetchFn?: FetchFn) =>
      post(`${userStoryId}/reveal`, fetchFn),

    /**
     * Select user story for voting
     */
    select: (userStoryId: number, fetchFn?: FetchFn) =>
      post(`${userStoryId}/select`, fetchFn),
  };
};
