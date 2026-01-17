import type { PageServerLoad } from "./$types";
import * as api from "$lib/shared/api/index";
import { EUserRole } from "$lib/shared/enums/user";
import { createGuestSchema } from "$lib/shared/schemas/sprint";
import { zod } from "sveltekit-superforms/adapters";
import { superValidate } from "sveltekit-superforms";
import { createGuestRequest } from "$lib/server/request/sprint";

export const load: PageServerLoad = async ({
  fetch,
  params,
  locals,
  parent,
}) => {
  const { userSprints, user } = await parent();
  const form = await superValidate(zod(createGuestSchema(locals.t)));

  const sprintId = Number(atob(params.id));

  const isJoinable =
    userSprints?.joinable?.some((s) => s.id === sprintId) ?? false;
  let isJoined = userSprints?.joined?.some((s) => s.id === sprintId) ?? false;

  if (user?.role === EUserRole.GUEST && !isJoined) {
    // Guests join any sprint automatically
    const responseForJoin = await api.join(sprintId, fetch);
    if (responseForJoin.success) {
      isJoined = true;
    }
  }

  const sprintResponse = await api.getSprint(
    sprintId,
    ["userStories", "estimations", "members", "estimationResults"],
    fetch
  );

  const sprint = sprintResponse.success ? sprintResponse.result : null;
  if (sprint) {
    if (userSprints?.error === "UN_AUTHORIZED") {
      return {
        form,
        sprintResponse: {
          success: false,
          result: { error: "UN_AUTHORIZED" },
        },
        sprint: null,
      };
    }

    if (!isJoined && !isJoinable) {
      return {
        form,
        sprintResponse: {
          success: false,
          result: { error: "SPRINT_NOT_ACCESSIBLE" },
        },
        sprint: null,
      };
    }

    if (!isJoined && isJoinable) {
      return {
        form,
        sprintResponse: {
          success: false,
          result: { error: "JOIN_TO_VOTE" },
        },
        sprint: null,
      };
    }
  }

  return { form, sprintResponse, sprint };
};

export const actions = {
  createGuest: async (event) => {
    return createGuestRequest(event);
  },
};
