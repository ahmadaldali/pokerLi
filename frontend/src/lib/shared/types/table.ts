  export type TableColumn<T> = {
    key: keyof T;
    label: string;
    class?: string;
    format?: (value: T[keyof T], row: T) => string;
  };

  export type TableAction<T> = {
    label: string;
    href: (row: T) => string;
    class?: string;
  };

  export type TableConfig<T> = {
    columns: TableColumn<T>[];
    actions?: TableAction<T>[];
  };