import type { UserRole } from "../enums/user";

export type TCurrentLoggedInUser = {
    id: string;
    name: string;
    email: string;
    role: UserRole;
};