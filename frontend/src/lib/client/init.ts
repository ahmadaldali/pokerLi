import { getLocales } from "$i18n/utils";
import { initServices as initSharedServices } from '$lib/shared/services';

/** Initializes all client third party services. */
export async function init() {
    await getLocales();
    initSharedServices();
}
