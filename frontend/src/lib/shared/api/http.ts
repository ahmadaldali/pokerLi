import { get } from "svelte/store";
import type { ApiOptionsType } from "$lib/shared/types/http";
import { tokenStore } from "$lib/shared/stores/user";
import { redirectTo } from "$lib/shared/utils/redirect";

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

    console.log("API CALL:", {
      url: options.url,
      method: options.method || "GET",
      headers: options.headers,
      data: options.data,
    });

    const response = await fetchMethod(options.url, {
      method: options.method || "GET",
      headers: apiHeaders(options.headers),
      body:
        options.data && options.method !== "GET"
          ? JSON.stringify(options.data)
          : null,
    });

    // 'result' = backend response (errors or data)
    if (response.status === 401) {  
      // Unauthorized - token might be expired or invalid
      console.warn("Unauthorized access - invalid or expired token.");
      await redirectTo('/logout');
      return;
    }

    if (!response.ok) {
      const text = await response.text();
      console.warn("HTTP ERROR RESPONSE:", text);
      return {
        result: JSON.parse(text),
        success: false,
      };
    }

    const contentType = response.headers.get("content-type");
    if (!contentType?.includes("application/json")) {
      const text = await response.text();
      console.warn("NON-JSON RESPONSE:", text);
      return {
        result: text,
        success: false,
      };
    }

    return {
      result: JSON.parse(await response.text()),
      success: true,
    };
  } catch (err: unknown) {
    if (err instanceof Error) {
      throw new Error(err.message, { cause: err });
    } else {
      throw new Error("Unhandled error type", { cause: err });
    }
  }
};

export const getL18ErrorMessage = (errors: any, code: string | null | undefined) => {
  if (!code) return null;
  
  code = code.toString().toUpperCase();
  // return message related to that code, otherwise if that code is not regonized, then return internal error message
  return errors[code]() ? errors[code]() : errors["INTERNAL_SERVER_ERROR"]();
};
