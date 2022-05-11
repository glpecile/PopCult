import i18n from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import { initReactI18next } from 'react-i18next';
import localeEN from './locales/en/translation.json';
import localeES from './locales/es/translation.json';

// https://react.i18next.com/legacy-v9/step-by-step-guide
const resources = {
    en: {
        translation: localeEN
    },
    es: {
        translation: localeES
    }
};

i18n
    // detect user language
    // learn more: https://github.com/i18next/i18next-browser-languageDetector
    .use(LanguageDetector)
    // pass the i18n instance to react-i18next.
    .use(initReactI18next)
    // init i18next
    // for all options read: https://www.i18next.com/overview/configuration-options
    .init({
        resources,
        fallbackLng: 'en',

        keySeparator: false, // we do not use keys in form messages.welcome
        debug: true,

        interpolation: {
            escapeValue: false, // not needed for react as it escapes by default
        },
    });

export default i18n;