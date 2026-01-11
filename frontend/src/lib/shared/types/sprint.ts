import type { TUser } from "./user";

export interface TUserStoryEstimation {
    estimation: number;
    user: TUser;
    estimationResultId: number;
    date: Date;
}

export interface TUserStoryEstimationResult {
    id: number;
    total: number;
    estimation: number;
    count: number;
}

export interface TUserStory {
    id: number;
    name: string;
    description: string;
    link: string;
    isRevealed: boolean;
    voters?: number[];
    isActive: boolean; // voting ongoing for this us. we keep only one us active at the same moment
    estimations?: TUserStoryEstimation[];
    estimationResults?: TUserStoryEstimationResult[];
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