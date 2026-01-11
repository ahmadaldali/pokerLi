import type { PageServerLoad } from "./$types";
import * as api from "$lib/shared/api/public";

export const load: PageServerLoad = async ({ fetch, params, parent }) => {
  const { userSprints } = await parent();

  const sprintResponse = await api.getSprint(
    params.id,
    ["userStories", "estimations", "members", "estimationResults"],
    fetch
  );

  const sprint = sprintResponse.success ? sprintResponse.result : null;

  if (sprint) {
    
    if (userSprints?.error === "UN_AUTHORIZED") {
      return {
        sprintResponse: {
          success: false,
          result: { error: "UN_AUTHORIZED" }
        },
        sprint: null
      };
    }

    const isJoined =
      userSprints?.joined?.some(s => s.id === sprint.id) ?? false;

    const isJoinable =
      userSprints?.joinable?.some(s => s.id === sprint.id) ?? false;

    if (!isJoined && !isJoinable) {
      return {
        sprintResponse: {
          success: false,
          result: { error: "SPRINT_NOT_ACCESSIBLE" }
        },
        sprint: null
      };
    }
  }

  return { sprintResponse, sprint };
};
