import type { TButtonVariant } from "./button";

export type TTableColumn<T> = {
  key: string;
  label: string;
  width?: string;
  class?: string;
  format?: (value: any, row: T) => string;
};

export type TTableAction<T> = {
  label: string;
  onClick: (row: T) => void;
  class?: string;
  disabled?: (row: T) => boolean;
  variant?: TButtonVariant;
};

export type TTableConfig<T> = {
  columns: TTableColumn<T>[];
  actions?: TTableAction<T>[];
};
