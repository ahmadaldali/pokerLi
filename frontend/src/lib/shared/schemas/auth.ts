import { z } from "zod";
import { lengthString, requiredString } from "../utils/validator";
import type { TranslationFunctions } from "$i18n/i18n-types";
import { emailField, nameField, passwordField } from "./fields";

export const loginSchema = (t: TranslationFunctions) =>
  z.object({
    email: emailField(t),
    password: requiredString(t, "Password"),
  });

export const signupSchema = (t: TranslationFunctions) =>
  z
    .object({
      name: nameField(t),
      email: emailField(t),
      password: passwordField(t).and(lengthString(t, "Password", 6, 50)),
      confirmPassword: requiredString(t, "Confirm Password"),
    })
    .refine((data) => data.password === data.confirmPassword, {
      path: ["confirmPassword"],
      message: t.errors.password_mismatch(),
    });
