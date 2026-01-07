import { api } from './http';
import { PUBLIC_API_URL } from '$env/static/public';

const MODULE_ROUTE = 'public';

export const getSprint = (id: string, fetchFn?: typeof fetch) => {
    return Promise.resolve(api({
        url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/sprints/${id}`,
        method: 'GET',
        fetch: fetchFn,
    }));
};