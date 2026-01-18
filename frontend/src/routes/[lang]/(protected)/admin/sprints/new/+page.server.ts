import { fail } from "@sveltejs/kit";
import type { PageServerLoad, Actions } from "./$types";
import { setSession } from "$lib/server/middleware/session.js";
import { message, superValidate } from "sveltekit-superforms";
import { zod } from "sveltekit-superforms/adapters";
import { redirectTo } from "$lib/shared/utils/redirect";
import { authApi, sprintsApi } from "$lib/shared/api";
import { createSprintSchema } from "$lib/shared/schemas/sprint";
import { redirect } from "@sveltejs/kit";
import { EUserRole } from "$lib/shared/enums/user";

export const load: PageServerLoad = async ({ locals, parent }) => {
  const form = await superValidate(zod(createSprintSchema(locals.t)));

  // Ensure only admin users can access this page - as we break the layout load chain.
  const { user } = await parent();
  if (!user || user.role !== EUserRole.ADMIN) {
    throw redirect(302, "/");
  }

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
