import { api } from './http';
import { PUBLIC_API_URL } from '$env/static/public';
import type { TSprint } from '../types/sprint';
import type { TApiResponse, TSuccessResponse } from '../types/http';

const MODULE_ROUTE = 'sprints';



export const startNewVoting = (
  sprintId: number,
  fetchFn?: typeof fetch
): Promise<TApiResponse<TSprint>> => {
  return Promise.resolve(
    api({
      url: `${PUBLIC_API_URL}/${MODULE_ROUTE}/${sprintId}/start-new-voting`,
      method: "POST",
      fetch: fetchFn,
      data: {},
    })
  );
};
