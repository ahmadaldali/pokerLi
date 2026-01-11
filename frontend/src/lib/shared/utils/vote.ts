import { unVote, vote } from "$lib/shared/api/vote";
import type { TApiResponse, TSuccessResponse } from "$lib/shared/types/http";

export const toggleEstimationVote = async (
  userStoryId: number,
  estimation: number,
  oldValue: number | null
) => {
  let newValue = estimation as number | null;
  let response: TApiResponse<TSuccessResponse> | null = null;
  if (estimation === oldValue) {
    response = await unVote(userStoryId);
    newValue = null;
  } else {
    response = await vote(userStoryId, {
      estimation: estimation,
    });
    newValue = estimation;
  }

  return {
    apiResponse: response,
    newValue,
  };
};
