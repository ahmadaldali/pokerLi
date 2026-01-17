import { getR } from './http';
import { PUBLIC_API_URL } from '$env/static/public';
import type { TSprint } from '../types/sprint';
import type { TApiResponse } from '../types/http';

const MODULE_ROUTE = 'public';

export const getSprint = (
	id: number,
	include?: string | string[],
	fetchFn?: typeof fetch
): Promise<TApiResponse<TSprint>> => {
	const params = new URLSearchParams();

	if (include) {
		params.set(
			'include',
			Array.isArray(include) ? include.join(',') : include
		);
	}

	const query = params.toString();
	const url = `${PUBLIC_API_URL}/${MODULE_ROUTE}/sprints/${id}${query ? `?${query}` : ''}`;

	return getR(url, fetchFn);
};