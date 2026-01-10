  export type TTableColumn<T> = {
    key: keyof T;
    label: string;
    class?: string;
    format?: (value: T[keyof T], row: T) => string;
  };

  export type TTableAction<T> = {
    label: string;
    href: (row: T) => string;
    class?: string;
  };

  export type TTableConfig<T> = {
    columns: TTableColumn<T>[];
    actions?: TTableAction<T>[];
  };