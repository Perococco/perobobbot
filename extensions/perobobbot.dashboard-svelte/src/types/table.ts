
export interface Column<T,A> {
    i18nKey:string,
    getter: (t:T) => A;
}

export interface Table<T> {
    columns:Column<T,any>[];
}
