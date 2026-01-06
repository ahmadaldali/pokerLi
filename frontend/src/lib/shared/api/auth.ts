import type { RawData } from '../types/general';
import { api } from './http';
import { PUBLIC_API_URL } from '$env/static/public';

const MODULE_ROUTE = 'auth';

export const register = (data: RawData) => {
	return Promise.resolve(api({
        url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/register`,
        method: 'POST',
        data: data
    }));
};

export const login = (data: RawData, fetchFn?: typeof fetch) => {
		return Promise.resolve(api({
        url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/login`,
        method: 'POST',
        fetch: fetchFn, 
        data: data
    }));
};