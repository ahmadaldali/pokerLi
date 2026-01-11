import type { UserRole } from "../enums/user";

export type TUser = {
    id: string;
    name: string;
    email: string;
    role: UserRole;
};