import type { variants } from "../utils/ui/button";

export type TButtonSize = 'xm' | 'sm' | 'md' | 'lg';

export type TButtonType = 'button' | 'submit' | 'reset';

export type TButtonVariant = keyof typeof variants;