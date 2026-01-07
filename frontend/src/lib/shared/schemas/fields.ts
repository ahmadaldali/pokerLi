import type { TranslationFunctions } from "$i18n/i18n-types";
import { z } from "zod";

export const passwordField = (t: TranslationFunctions) => z.string()
    .regex(/[A-Z]/, t.errors.one_uppercase({ field: "Password" }))
    .regex(/[a-z]/, t.errors.one_lowercase({ field: "Password" }))
    .regex(/[0-9]/, t.errors.one_number({ field: "Password" }))
    .regex(/[\W_]/, t.errors.one_special({ field: "Password" }));

export const emailField = (t: TranslationFunctions) => z.string().email(t.errors.invalid_email())