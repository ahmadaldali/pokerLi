
import type { TranslationFunctions } from "$i18n/i18n-types";
import { z } from "zod";
import { requiredString } from "../utils/validator";
import { nameField } from "./fields";

export const createGuestSchema = (t: TranslationFunctions) =>
  z.object({
    name: nameField(t),
    guestId: requiredString(t, "guestId"),
  });