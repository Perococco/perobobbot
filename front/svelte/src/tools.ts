
export function isBlank(value:string|null|undefined):boolean {
    if (value == undefined) {
        return true;
    }
    return value.trim().length == 0;
}

export function hasValue(value:string|null|undefined):boolean {
    return !isBlank(value);
}

export function toMap<K,V>(values:V[], keyGetter:(v:V) => K):Map<K,V> {
    const result:Map<K,V> = new Map<K, V>();
    for (const value of values) {
        result.set(keyGetter(value),value);
    }
    return result;
}
