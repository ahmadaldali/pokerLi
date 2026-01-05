import { get } from "svelte/store";
import type { ApiOptionsType } from "$lib/shared/types/http";
import { tokenStore } from "$lib/shared/stores/user";

const apiHeaders = (additionalHeaders: Record<string, string>) => {
  const defaultHeaders = {
    "Content-Type": "application/json",
    Accept: "application/json",
  };

  return additionalHeaders
    ? { ...defaultHeaders, ...additionalHeaders }
    : defaultHeaders;
};

export const api = async (options: ApiOptionsType) => {
  const fetchMethod = options.fetch || fetch;
  try {
    const token = get(tokenStore);
    if (token) {
      options.headers = {
        ...options.headers,
        Authorization: `Bearer ${token}`,
      };
    }
    
 
    const response = await fetchMethod(options.url, {
      method: options.method || "GET",
      headers: apiHeaders(options.headers),
      body:
        options.data && options.method !== "GET"
          ? JSON.stringify(options.data)
          : null,
    });

    const res = await response.json();

    console.log("API RESPONSE", res);
    return res;
  } catch (err: unknown) {
    if (err instanceof Error) {
      throw new Error(err.message, { cause: err });
    } else {
      throw new Error("Unhandled error type", { cause: err });
    }
  }
};
