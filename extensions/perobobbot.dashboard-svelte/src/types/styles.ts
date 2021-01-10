import {Optional} from "./optional";

export class Styles {
    private background: string = "";

    public withoutBackground(): Styles {
        return this.withBackground("none");
    }

    public withBackgroundForRoute(route:string):Styles {
        return getBackground(route)
            .map(bkg => this.withBackground(bkg))
            .orElse(this);
    }

    public withBackground(background: string): Styles {
        this.background = background;
        return this;
    }

    public getBackground(): string {
        return this.background;
    }
}

function getBackground(route: string):Optional<string> {
    switch (route) {
        case "%%/login": return Optional.of(backgroundWithPlainColor("var(--background-login)"));
        case "%%/welcome": return Optional.of(backgroundWithPlainColor("var(--background-welcome)"));
        case "%%/home": return Optional.of(backgroundWithPlainColor("var(--background-home)"));
    }
    return Optional.empty();
}

function backgroundWithImage(image:string): string {
    return 'url(assets/' + image + ') no-repeat center center fixed';
}

function backgroundWithPlainColor(clr:string): string {
    return clr+' no-repeat center center fixed';
}



const NEBULA_LARGE:string = "nebula_large_light.png";

export const GS_WELCOME_BKG = NEBULA_LARGE;
export const GS_LOGIN_BKG = NEBULA_LARGE;
export const GS_HOME_BKG = NEBULA_LARGE;
