import { fail, redirect } from "@sveltejs/kit";
import type { PageServerLoad, Actions } from "./$types";
import * as api from "$lib/shared/api/auth";
import { setSession } from "$lib/server/middleware/session.js";
import { message, superValidate } from "sveltekit-superforms";
import { zod } from "sveltekit-superforms/adapters";
import { loginSchema, signupSchema } from "$lib/shared/schemas";
import { redirectTo } from "$lib/shared/utils/redirect";

export const load: PageServerLoad = async ({ locals }) => {
  const form = await superValidate(zod(signupSchema(locals.t)));

  return { form };
};


export const actions: Actions = {
  default: async ({ request, cookies, fetch, locals }) => {
    const formData = await request.formData();
    const form = await superValidate(formData, zod(signupSchema(locals.t)));

    if (!form.valid) {
      return fail(400, { form });
    }

    const { name, email, password } = form.data;
    const response = await api.register({ name, email, password }, fetch);

    if (!response.success) {
      return message(form, response);
    }

    // go to homepage after successful login
    setSession(cookies, response.result.token);
    redirectTo(locals.t.routes.user.homepage());
  },
};
