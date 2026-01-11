import { writable } from "svelte/store";
import type { TUserStory } from "../types/sprint";

export const selectedUserStroyStore = writable<TUserStory | null>(null);
export const userStoryEstimationStore = writable<number | null>(null);

// setters

export function updateEstimation(estimation: number | null) {
    userStoryEstimationStore.update((current) => current = estimation);
}

export function resetEstimation() {
    userStoryEstimationStore.set(null);
}