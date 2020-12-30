export class Optional<T> {
    private value?:T;

    private static Empty = new Optional<any>(undefined);

    constructor(value?: T) {
        this.value = value;
    }

    public map<U>(mapper:(t:T) => U):Optional<U> {
        if (this.value == undefined) {
            return Optional.Empty;
        }
        return Optional.ofNullable(mapper(this.value));
    }

    public flatMap<U>(mapper:(t:T) => Optional<U>):Optional<U> {
        if (this.value == undefined) {
            return Optional.Empty;
        }
        return mapper(this.value);
    }

    public get():T {
        if (this.value == undefined) {
            throw new Error("Retrieve value from empty optional");
        }
        return this.value;
    }

    public orElse(defaultValue:T):T {
        return this.value??defaultValue;
    }

    public orUndefined():T|undefined {
        return this.value??undefined;
    }

    public orElseGet(defaultValue:()=>T|undefined):T|undefined {
        return this.value??defaultValue();
    }

    public isPresent():boolean {
        return this.value != undefined;
    }

    public isAbsent():boolean {
        return this.value == undefined;
    }

    public ifPresent(action:(t:T)=>void):void {
        if (this.value != undefined) {
            action(this.value);
        }
    }


    static of<T>(value:T):Optional<T> {
        return new Optional<T>(value);
    }

    static ofNullable<T>(value:T|undefined|null):Optional<T> {
        if (value == undefined) {
            return Optional.Empty;
        } else {
            return new Optional<T>(value as T);
        }
    }

    static empty<T>():Optional<T> {
        return this.Empty;
    }

}
