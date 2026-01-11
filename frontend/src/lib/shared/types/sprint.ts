import type { TUser } from "./user";

export interface TUserStoryEstimation {
    estimation: number;
    user: TUser;
}

export interface TUserStory {
    id: number;
    name: string;
    description: string;
    link: string;
    isRevealed: boolean;
    voters?: number[];
    estimations?: TUserStoryEstimation[];
}

export interface TSprint {
    id: number;
    name: string;
    sequence: number[];
    creator: string;
    userStories?: TUserStory[]
    members?: TUser[];
}

export interface TUserSprintsApiResponse {
    joined: TSprint[];
    joinable: TSprint[];
}