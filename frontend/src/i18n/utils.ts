import { loadAllLocales } from "./i18n-util.sync";

export async function getLocales() {
    loadAllLocales();
}