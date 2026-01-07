import type { TranslationFunctions } from "$i18n/i18n-types";
import { z } from "zod";

export const requiredString = (t: TranslationFunctions, field: string) => z.string().min(1, t.errors.required({ field }));

export const minLengthString = (t: TranslationFunctions, field: string, min: number) =>
  z.string().min(min, t.errors.min_length({ field, min }));

export const maxLengthString = (t: TranslationFunctions, field: string, max: number) =>
  z.string().max(max, t.errors.max_length({ field, max })); 

export const lengthString = (t: TranslationFunctions, field: string, min: number, max: number) =>
  z.string()
    .min(min, t.errors.min_length({ field, min }))
    .max(max, t.errors.max_length({ field, max }));