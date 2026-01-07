import { fail, redirect } from "@sveltejs/kit";
import type { PageServerLoad, Actions } from "./$types";
import * as api from "$lib/shared/api/index";
import { setSession } from "$lib/server/middleware/session.js";
import { message, superValidate } from "sveltekit-superforms";
import { zod } from "sveltekit-superforms/adapters";
import { loginSchema, signupSchema } from "$lib/shared/schemas";
import { redirectTo } from "$lib/shared/utils/redirect";

export const load: PageServerLoad = async () => {
  const form = await superValidate(zod(signupSchema));

  return { form };
};