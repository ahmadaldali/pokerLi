import type { RawData } from "./general";
import type { HttpMethod, RequestEvent } from '@sveltejs/kit';

export type Session = {
    token: string;
};

export type ApiOptionsType = {
    fetch?: any;
    url: string;
    method?: HttpMethod;
    headers?: any;
    data?:  RawData;
    event?: RequestEvent;
};