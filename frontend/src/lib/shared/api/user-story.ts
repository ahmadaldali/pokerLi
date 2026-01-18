import { post } from "./http";
import { PUBLIC_API_URL } from "$env/static/public";
import type { TApiResponse, TSuccessResponse } from "$lib/shared/types/http";
import type { TUserStory } from "../types/sprint";

const MODULE_ROUTE = "user-stories";

type FetchFn = typeof fetch;

type VotePayload = {
  estimation: number;
};

/**
 * User Stories API
 */
export const userStoriesApi = () => {
  return {
    /**
     * Vote on a user story
     */
    vote: (
      userStoryId: number,
      data: VotePayload,
      fetchFn?: FetchFn
    ): Promise<TApiResponse<TSuccessResponse>> =>
      post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/${userStoryId}/vote`, fetchFn, data),

    /**
     * Remove current vote
     */
    unVote: (userStoryId: number, fetchFn?: FetchFn): Promise<TApiResponse<TSuccessResponse>> =>
      post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/${userStoryId}/un-vote`, fetchFn),

    /**
     * Vote again after reveal
     */
    voteAgain: (userStoryId: number, fetchFn?: FetchFn): Promise<TApiResponse<TUserStory>> =>
      post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/${userStoryId}/vote-again`, fetchFn),

    /**
     * Reveal votes
     */
    reveal: (userStoryId: number, fetchFn?: FetchFn): Promise<TApiResponse<TSuccessResponse>> =>
      post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/${userStoryId}/reveal`, fetchFn),

    /**
     * Select user story for voting
     */
    select: (userStoryId: number, fetchFn?: FetchFn): Promise<TApiResponse<TUserStory>> =>
      post(`${PUBLIC_API_URL}/${MODULE_ROUTE}/${userStoryId}/select`, fetchFn),
  };
};
