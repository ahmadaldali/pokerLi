import { api } from "./http";
import { PUBLIC_API_URL } from "$env/static/public";
import type { TApiResponse, TSuccessResponse } from "$lib/shared/types/http";

const MODULE_ROUTE = "user-stories";

export const vote = (
  userStoryId: number,
  data: {
    estimation: number;
  },
  fetchFn?: typeof fetch
): Promise<TApiResponse<TSuccessResponse>> => {
  return Promise.resolve(
    api({
      url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/${userStoryId}/vote`,
      method: "POST",
      fetch: fetchFn,
      data: data,
    })
  );
};

export const unVote = (
  userStoryId: number,
  fetchFn?: typeof fetch
): Promise<TApiResponse<TSuccessResponse>> => {
  return Promise.resolve(
    api({
      url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/${userStoryId}/un-vote`,
      method: "POST",
      fetch: fetchFn,
      data: {},
    })
  );
};

export const voteAgain = (
  userStoryId: number,
  fetchFn?: typeof fetch
): Promise<TApiResponse<TSuccessResponse>> => {
  return Promise.resolve(
    api({
      url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/${userStoryId}/vote-again`,
      method: "POST",
      fetch: fetchFn,
      data: {},
    })
  );
};

export const reveal = (
  userStoryId: number,
  fetchFn?: typeof fetch
): Promise<TApiResponse<TSuccessResponse>> => {
  return Promise.resolve(
    api({
      url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/${userStoryId}/reveal`,
      method: "POST",
      fetch: fetchFn,
      data: {},
    })
  );
};
