import type { TRawData } from '../types/general';
import { api } from './http';
import { PUBLIC_API_URL } from '$env/static/public';
import type { TUserSprintsApiResponse } from '../types/sprint';
import type { TApiResponse } from '../types/http';

const MODULE_ROUTE = 'users';

export const getUser = () => {
	return Promise.resolve(api({
        url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/me`,
        method: 'GET'
    }));
};

export const getUserSprints = (fetchFn?: typeof fetch): Promise<TApiResponse<TUserSprintsApiResponse>> => {
	return Promise.resolve(api({
        url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/sprints`,
        method: 'GET',
            fetch: fetchFn, 
    }));
};