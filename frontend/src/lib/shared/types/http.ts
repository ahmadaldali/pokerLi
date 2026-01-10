import type { TRawData } from "./general";
import type { HttpMethod, RequestEvent } from '@sveltejs/kit';

export type TApiOptions = {
    fetch?: typeof fetch;  // Add this
    url: string;
    method?: HttpMethod;
    headers?: any;
    data?:  TRawData;
    event?: RequestEvent;
};

export type TApiResponse<T = any> = {
    success: boolean;
    result: T & {
        error?: string | null;
    };
}

export type TSuccessResponse = {
    message: string;
}