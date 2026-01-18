import type { TButtonSize, TButtonVariant } from "$lib/shared/types/button";

const base = `
  inline-flex items-center justify-center gap-2
  font-semibold transition
  focus:outline-none focus:ring-2
  focus:ring-offset-2 focus:ring-offset-slate-900
  disabled:cursor-not-allowed
  `;

const sizes: Record<TButtonSize, string> = {
  xm: "px-3 py-1.5 text-xs",
  sm: "px-4 py-2 text-sm",
  md: "px-6 py-2.5 text-sm",
  lg: "px-8 py-3 text-base",
};

export const variants = {
  /** Main app action */
  primary: `
    bg-emerald-600 text-slate-900
    hover:text-slate-900 hover:bg-emerald-700
    disabled:bg-emerald-700
    focus:ring-emerald-500
  `,

  /** Light red — reset, cancel voting, destructive but safe */
  danger: `
    bg-red-500 text-white
    hover:bg-red-400
    disabled:bg-red-700 disabled:text-white/60
    focus:ring-red-400
    border border-white/70
  `,

  /** White text, transparent background */
  ghost: `
    border border-white/70
    text-white
    hover:bg-white/10
    hover:border-white
    disabled:border-white/30
    disabled:text-white/40
    focus:ring-white/40
  `,

  /** Border version */
  outline: `
    border border-emerald-500 text-emerald-400
    hover:bg-emerald-500 hover:text-slate-200
    disabled:border-emerald-700 disabled:text-slate-600
    focus:ring-emerald-500
  `,

  /** Reserved / neutral */
  solid: `
    bg-slate-700 text-slate-100
    hover:bg-slate-600
    disabled:bg-slate-800
    focus:ring-slate-500
  `,

  /** No chrome – for icon buttons, inline actions */
  none: `
    bg-transparent
    border-none
    p-0
    text-inherit
    hover:bg-white/5
    disabled:text-slate-500
    focus:ring-white/30
  `,
};

export const getButtonStyle = ({
  size,
  variant = "primary",
  rounded,
  loading,
  fullWidth,
}: {
  size: TButtonSize;
  variant?: TButtonVariant;
  rounded?: boolean;
  loading?: boolean;
  fullWidth?: boolean;
}): string =>
  [
    base,
    sizes[size],
    variants[variant],
    rounded ? "rounded-2xl" : "rounded-lg",
    fullWidth ? "w-full" : "w-auto",
    loading ? "opacity-70 cursor-wait" : "",
  ]
    .filter(Boolean)
    .join(" ");
