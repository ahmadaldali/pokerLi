import type { PageServerLoad } from "./$types";
import * as api from "$lib/shared/api/public";

export const load: PageServerLoad = async ({ locals, fetch, params, parent }) => {

  console.log('Loading sprint details for id:', (await parent()).userSprints);

const userSprints = (await parent()).userSprints;

 const sprintResponse = await api.getSprint(params.id, 'userStories' , fetch);

 const sprint = sprintResponse.success ? sprintResponse.result : null;


  // check if sprint is joinable or joined by the user
  if (sprint) {
    const isJoined = userSprints?.joined?.some(s => s.id === sprint.id);
    const isJoinable = userSprints?.joinable?.some(s => s.id === sprint.id);

if (userSprints.error == "UN_AUTHORIZED") {
  // tryin to access public sprint without being logged in
  return {
    sprintResponse: {
      success: false,
      result: { error: "UN_AUTHORIZED" },
    },
    userStories: [],
    sprint: null
  };
}

    if (!isJoined && !isJoinable) {
      return {
        sprintResponse: {
          success: false,
          result: { error: "SPRINT_NOT_ACCESSIBLE" },
        },
        userStories: [],
        sprint: null
      };
    }
  }
  
  return { sprintResponse, sprint, userStories: sprint?.userStories || [] };
};