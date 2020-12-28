import {background} from "../stores/background";

export class GlobalStyle {
    private background:string = "";

    public withoutBackground():GlobalStyle {
        return this.withBackground("none");
    }

    public withBackgroundImage(image:string):GlobalStyle {
        return this.withBackground('url(assets/'+image+') no-repeat center center fixed');
    }

    public withBackground(background:string):GlobalStyle {
        this.background = background;
        return this;
    }

    public getBackground():string {
        return this.background;
    }
 }