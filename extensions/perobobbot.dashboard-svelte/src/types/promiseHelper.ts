
export class PromiseHelper<T> {
    readonly promise: Promise<T>;
    private resolver: (value: T | PromiseLike<T>) => void;
    private rejecter: (reason?: any) => void;

    constructor() {
        this.resolver = o => {
        };
        this.rejecter = a => {
        };
        this.promise = new Promise<T>((resolve, reject) => {
            this.resolver = resolve;
            this.rejecter = reject;
        })
    }

    public reject(reason?: any) {
        this.rejecter(reason);
    }

    public resolve(value: T | PromiseLike<T>) {
        this.resolver(value);
    }
}
