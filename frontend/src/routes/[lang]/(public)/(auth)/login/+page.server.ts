import { fail } from "@sveltejs/kit";
import type { PageServerLoad, Actions } from "./$types";
import { setSession } from "$lib/server/middleware/session.js";
import { message, superValidate } from "sveltekit-superforms";
import { zod } from "sveltekit-superforms/adapters";
import { loginSchema } from "$lib/shared/schemas";
import { redirectTo } from "$lib/shared/utils/redirect";
import { authApi } from "$lib/shared/api";

export const load: PageServerLoad = async ({ locals }) => {
  const form = await superValidate(zod(loginSchema(locals.t)));

  return { form };
};

export const actions: Actions = {
  default: async ({ request, cookies, fetch, locals }) => {
    const formData = await request.formData();
    const form = await superValidate(formData, zod(loginSchema(locals.t)));

    if (!form.valid) {
      return fail(400, { form });
    }

    const { email, password } = form.data;
    const response = await authApi().login({ email, password }, fetch);

    if (!response.success) {
      return message(form, response);
    }

    // go to homepage after successful login
    setSession(cookies, response.result.token);
    redirectTo(locals.t.routes.user.homepage());
  },
};
