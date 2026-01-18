
import type { TApiResponse, TSuccessResponse } from "$lib/shared/types/http";
import { userStoriesApi } from "../api/user-story";

export const toggleEstimationVote = async (
  userStoryId: number,
  estimation: number,
  oldValue: number | null
) => {
  let newValue = estimation as number | null;
  let response: TApiResponse<TSuccessResponse> | null = null;
  if (estimation === oldValue) {
    response = await userStoriesApi().unVote(userStoryId);
    newValue = null;
  } else {
    response = await userStoriesApi().vote(userStoryId, {
      estimation: estimation,
    });
    newValue = estimation;
  }

  return {
    apiResponse: response,
    newValue,
  };
};
