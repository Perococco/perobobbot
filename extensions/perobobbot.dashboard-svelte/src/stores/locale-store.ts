import * as Svelte from 'svelte-i18n';
import {getLocaleFromNavigator, init, register} from 'svelte-i18n';
import type {Readable, Writable} from "svelte/types/runtime/store";
import {I18nController} from "@backend/rest-controller";
import {Optional} from "../types/optional";
import Locale = Intl.Locale;

export interface BotLocale extends Writable<string> {
    initialize:()=>Promise<void>;
}

export interface BotLocales extends Readable<string[]> {
}

interface Context {
    availableLocales:Locale[];
    fallbackLocale:string;
}

const LOCALE_KEY: string = "APP_LOCALE";

const controller = new I18nController();

function projectAndSet(locale:string,context:Context):string {
    const availableLocales = context.availableLocales;
    const fallbackLocale = context.fallbackLocale;
    const projectedLocale = bestMatch(locale, availableLocales).orElse(fallbackLocale);
    localStorage.setItem(LOCALE_KEY,projectedLocale);
    return projectedLocale;
}


async function _initialize(botLocale:BotLocale, context:Context):Promise<void> {
    const availableLanguageTags: string[] = await controller.getAvailableLanguageTags();
    context.fallbackLocale = availableLanguageTags.length == 0 ? "en" : availableLanguageTags[0];
    context.availableLocales = availableLanguageTags.map(t => new Intl.Locale(t))

    availableLanguageTags.forEach(l => register(l, () => controller.getDictionary(l)))

    botLocale.set(getFromStorageOrNavigator())
}

function createBotLocale(context:Context): BotLocale {

    init({
        fallbackLocale: 'en',
        initialLocale: 'en',
    });

    register("en",() => controller.getDictionary("xx"));

    let _value:string = "";

    return {
        subscribe: Svelte.locale.subscribe,
        update(updater: (v: string) => string) {
            Svelte.locale.update(u => projectAndSet(updater(u),context));
        },
        set(value: string) {
            const newValue:string = projectAndSet(value,context);
            if (newValue !== _value) {
                Svelte.locale.set(projectAndSet(value, context));
            }
        },
        initialize: async function():Promise<void> {
            return _initialize(this,context)
        }
    }
}


function bestMatch(providedLanguageTag: string, availableLocales: Locale[]): Optional<string> {
    const provided = new Locale(providedLanguageTag);
    let bestScore = 0;
    let bestResult: string | undefined = undefined;

    for (const locale of availableLocales) {
        const score = computeScore(locale, provided);
        if (score > bestScore) {
            bestScore = score;
            bestResult = locale.baseName;
        }
    }
    return Optional.ofNullable(bestResult);
}

function computeScore(locale1: Locale, locale2: Locale): number {
    let score = 0;
    if (locale1.language === locale2.language) {
        score++;
        if (locale1.region !== locale2.region) {
            score++;
            if (locale1.script === locale2.script) {
                score++;
            }
        }
    }
    return score;
}

function getFromStorageOrNavigator(): string {
    return Optional.ofNullable(localStorage.getItem(LOCALE_KEY))
        .orElseGet(getLocaleFromNavigator);
}

const botLocales: BotLocales = {
    subscribe: Svelte.locales.subscribe,
}
const botLocale: BotLocale = createBotLocale({fallbackLocale:'en',availableLocales:[]});


export {botLocales,botLocale};
