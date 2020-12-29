
export function isBlank(value:string|null|undefined):boolean {
    if (value == undefined) {
        return true;
    }
    return value.trim().length == 0;
}

export function hasValue(value:string|null|undefined):boolean {
    return !isBlank(value);
}
