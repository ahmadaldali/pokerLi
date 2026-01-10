export interface TUserStory {
    id: number;
    name: string;
    description: string;
    link: string;
    isVotingOver: boolean;
}

export interface TSprint {
    id: number;
    name: string;
    sequence: number[];
    creator: string;
    userStories?: TUserStory[]
}

export interface TUserSprintsApiResponse {
    joined: TSprint[];
    joinable: TSprint[];
}