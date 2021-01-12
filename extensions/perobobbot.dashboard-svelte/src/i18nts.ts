import {getLocaleFromNavigator, init, locale, register} from "svelte-i18n";
import {I18nController} from "./server/rest-controller";
import {Optional} from "./types/optional";

const LOCALE_KEY: string = "APP_LOCALE";
const controller = new I18nController();

/**
 * Register all the available dictionaries to svelte-i18n and
 * return the list of available locale (as languageTag)
 */
async function registerDictionaries(): Promise<string[]> {
    const languageTags = await controller.getAvailableLanguageTags();
    languageTags.forEach((languageTag, index, _) => {
        register(languageTag, () => controller.getDictionary(languageTag))
    })
    return languageTags;
}


function getInitialLocale(availableLocales: string[]): string {
    const candidate = getFromStorageOrNavigator();
    return mapToAnAvailableLocale(candidate, availableLocales);
}

export function getFromStorageOrNavigator(): string {
    return Optional.ofNullable(localStorage.getItem(LOCALE_KEY))
        .orElseGet(getLocaleFromNavigator);
}


function mapToAnAvailableLocale(locale: string, availableLocales: string[]): string {
    const defaultLocale: string = availableLocales.length == 0 ? "en" : availableLocales[0]
    if (locale == undefined) {
        return defaultLocale;
    }
    return availableLocales.find(l => matches(locale, l)) || defaultLocale;
}

function splitLocale(locale: string): string[] {
    const idxOfDash = locale.indexOf("-");
    if (idxOfDash >= 0) {
        return [locale, locale.slice(0, idxOfDash)];
    } else {
        return [locale];
    }
}

function matches(checked: string, available: string): boolean {
    console.log("Checked = " + checked);
    const split: string[] = splitLocale(Intl.getCanonicalLocales(checked)[0]);
    const canonical: string = Intl.getCanonicalLocales(available)[0];

    return split.find(s => s === canonical) !== undefined;
}

export async function initializeI18n(): Promise<void> {
    prepareI18n();
    const availableLocales = await registerDictionaries();
    const initialLocale = getInitialLocale(availableLocales);
    locale.set(initialLocale);
}

export function saveLocale(locale: any): void {
    if (locale == undefined) {
        localStorage.removeItem(LOCALE_KEY);
    } else {
        localStorage.setItem(LOCALE_KEY, locale);
    }
}


function prepareI18n() {
    register('en', () => controller.getDictionary('en'))
    init({
        fallbackLocale: 'en',
        initialLocale: 'en',
    });

}


