import { redirectTo } from "$lib/shared/utils/redirect";
import { fail, message, superValidate } from "sveltekit-superforms";
import { zod } from "sveltekit-superforms/adapters";
import { setSession } from "$lib/server/middleware/session";
import type { SprintPageRequestEvent } from "$lib/shared/types/request-event";
import { generateRandomString } from "$lib/shared/utils/helper";
import { authApi } from "$lib/shared/api";
import { createGuestSchema } from "$lib/shared/schemas";

export const createGuestRequest = async (event: SprintPageRequestEvent) => {
  const formData = await event.request.formData();
  formData.append("guestId", generateRandomString(32));
  const form = await superValidate(
    formData,
    zod(createGuestSchema(event.locals.t))
  );

  if (!form.valid) {
    return fail(400, { form });
  }

  const response = await authApi().createGuest(form.data, event.fetch);
  if (response.success) {
    setSession(event.cookies, response.result.token);
    redirectTo(event.locals.t.routes.sprints.details(event.params.id));
  }

  return message(form, response);
};
