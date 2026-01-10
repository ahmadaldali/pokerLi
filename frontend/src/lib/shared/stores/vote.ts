import { writable } from "svelte/store";
import type { TUserStory } from "../types/sprint";

export const selectedUserStroyStore = writable<TUserStory | null>(null);
export const estimationStore = writable<number | null>(null);