import {Optional} from "./optional";

export class Styles {
    private background: string = "";

    public withoutBackground(): Styles {
        return this.withBackground("none");
    }

    public withBackgroundForRoute(route:string):Styles {
        return getBackground(route)
            .map(img => this.withBackgroundImage(img))
            .orElse(this);
    }

    public withBackgroundImage(image: string): Styles {
        return this.withBackground('url(assets/' + image + ') no-repeat center center fixed');
    }

    public withBackground(background: string): Styles {
        this.background = background;
        return this;
    }

    public getBackground(): string {
        return this.background;
    }
}

export function getBackground(route: string):Optional<string> {
    switch (route) {
        case "/login": return Optional.of(NEBULA_LARGE);
        case "/welcome": return Optional.of(NEBULA_LARGE);
        case "/home": return Optional.of(NEBULA_LARGE);
    }
    return Optional.empty();

}


const NEBULA_LARGE:string = "nebula_large_dark.png";

export const GS_WELCOME_BKG = NEBULA_LARGE;
export const GS_LOGIN_BKG = NEBULA_LARGE;
export const GS_HOME_BKG = NEBULA_LARGE;
