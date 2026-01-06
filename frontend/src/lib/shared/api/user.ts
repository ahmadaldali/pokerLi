import type { RawData } from '../types/general';
import { api } from './http';
import { PUBLIC_API_URL } from '$env/static/public';

const MODULE_ROUTE = 'users';

export const getUser = () => {
	return Promise.resolve(api({
        url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/me`,
        method: 'GET'
    }));
};