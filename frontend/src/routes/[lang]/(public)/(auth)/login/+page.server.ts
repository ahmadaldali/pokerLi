import { fail, redirect } from "@sveltejs/kit";
import type { PageServerLoad, Actions } from "./$types";
import * as api from "$lib/shared/api/index";
import { setSession } from "$lib/server/middleware/session.js";
import { message, superValidate } from "sveltekit-superforms";
import { zod } from "sveltekit-superforms/adapters";
import { loginSchema } from "$lib/shared/schemas";
import { redirectTo } from "$lib/shared/utils/redirect";

export const load: PageServerLoad = async () => {
  const form = await superValidate(zod(loginSchema));

  return { form };
};

export const actions: Actions = {
  default: async ({ request, cookies, fetch, locals }) => {
    const formData = await request.formData();
    const form = await superValidate(formData, zod(loginSchema));

    if (!form.valid) {
      return fail(400, { form });
    }

    const { email, password } = form.data;
    const response = await api.login({ email, password }, fetch);

    if (!response.success) {
      return message(form, response);
    }

    // go to homepage after successful login
    setSession(cookies, response.result.token);
    redirectTo(locals.t.routes.user.homepage());
  },
};
