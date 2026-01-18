
import type { TranslationFunctions } from "$i18n/i18n-types";
import { z } from "zod";
import { nameField, sequenceField } from "./fields";

export const createSprintSchema = (t: TranslationFunctions) =>
  z.object({
    name: nameField(t),
    sequence: sequenceField(t),
  });



