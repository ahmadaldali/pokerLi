import type { RawData } from "./general";
import type { HttpMethod, RequestEvent } from '@sveltejs/kit';

export type ApiOptionsType = {
    fetch?: typeof fetch;  // Add this
    url: string;
    method?: HttpMethod;
    headers?: any;
    data?:  RawData;
    event?: RequestEvent;
};

export type ApiResponse = {
    success: boolean;
    result: {
        error: string | null;
        [key: string]: any;
    }
}