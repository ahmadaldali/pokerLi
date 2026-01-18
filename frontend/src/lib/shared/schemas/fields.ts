import type { TranslationFunctions } from "$i18n/i18n-types";
import { z } from "zod";
import { lengthString } from "../utils/validator";

export const passwordField = (t: TranslationFunctions) => z.string()
    .regex(/[A-Z]/, t.errors.one_uppercase({ field: "Password" }))
    .regex(/[a-z]/, t.errors.one_lowercase({ field: "Password" }))
    .regex(/[0-9]/, t.errors.one_number({ field: "Password" }))
    .regex(/[\W_]/, t.errors.one_special({ field: "Password" }));

export const emailField = (t: TranslationFunctions) => z.string().email(t.errors.invalid_email());

export const nameField = (t: TranslationFunctions) => lengthString(t, "Name", 3, 50);


export const sequenceField = (t: TranslationFunctions) =>
  z
    .string()
    .min(1, "sequence.required")
    .transform((value) => {
      const numbers = value
        .split(",")
        .map((v) => v.trim())
        .filter(Boolean)
        .map(Number);

      // ✅ remove duplicates
      return [...new Set(numbers)];
    })
    .refine(
      (arr) => arr.every((n) => !Number.isNaN(n)),
      "sequence.invalid_numbers"
    )
    .refine(
      (arr) => arr.length <= 10,
      "sequence.max_items"
    );
