import type { EUserRole } from "../enums/user";

export type TUser = {
    id: number;
    name: string;
    email?: string;
    role: EUserRole;
};