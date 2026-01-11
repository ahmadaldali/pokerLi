import type { TButtonSize } from "$lib/shared/types/button";

const buttonStyles = {
  style: `relative bg-emerald-500 hover:bg-emerald-400
               disabled:bg-emerald-700 disabled:cursor-not-allowed
               text-slate-950 font-semibold px-6 py-2.5
               transition focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:ring-offset-2 focus:ring-offset-slate-900`,
  size: {
    xm: "text-xs",
    sm: "text-sm",
    md: "text-md",
    lg: "text-lg",
  },
};

export const getButtonStyle = (
  size: TButtonSize,
  rounded: boolean,
  loading: boolean,
  fullWidth: boolean
): string =>
  [
    buttonStyles.style,
    buttonStyles.size[size],
    loading ? "cursor-not-allowed" : "cursor-pointer",
    rounded ? "rounded-2xl" : "rounded-md",
    fullWidth ? "w-full" : "w-auto",
  ].join(" ");
