import { fail } from "@sveltejs/kit";
import type { PageServerLoad, Actions } from "./$types";
import { setSession } from "$lib/server/middleware/session.js";
import { message, superValidate } from "sveltekit-superforms";
import { zod } from "sveltekit-superforms/adapters";
import { redirectTo } from "$lib/shared/utils/redirect";
import { authApi, sprintsApi } from "$lib/shared/api";
import { createSprintSchema } from "$lib/shared/schemas/sprint";

export const load: PageServerLoad = async ({ locals }) => {
  const form = await superValidate(zod(createSprintSchema(locals.t)));

  console.log(locals.user, "in new sprint creation")

  return { form };
};

export const actions: Actions = {
  default: async ({ request, cookies, fetch, locals }) => {
    const formData = await request.formData();
    const form = await superValidate(
      formData,
      zod(createSprintSchema(locals.t)),
    );

    if (!form.valid) {
      return fail(400, { form });
    }

    const { name, sequence } = form.data;

    const response = await sprintsApi().create({ name, sequence }, fetch);

    if (!response.success) {
      return message(form, response);
    }

    redirectTo(locals.t.routes.sprints.index());
  },
};
