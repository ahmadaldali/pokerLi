<script lang="ts">
  import type {
    TButtonSize,
    TButtonType,
    TButtonVariant,
  } from "$lib/shared/types/button";
  import { generateRandomString } from "$lib/shared/utils/helper";
  import { getButtonStyle } from "$lib/shared/utils/ui/button";
  import type { iconName } from "$lib/shared/utils/ui/icon";
  import SvgIcon from "./SvgIcon.svelte";

  // Specify the id of button
  export let id: string = generateRandomString();
  export let size: TButtonSize = "md";
  export let rounded: boolean = false;
  export let type: TButtonType = "button";
  export let disabled = false;
  export let loading: boolean = false;
  export let tabindex: number = 0;
  export let ref: HTMLElement | null = null;
  export let fullWidth: boolean = true;
  export let variant: TButtonVariant = "primary";

  export let icon: iconName | undefined = undefined;
  export let iconWidth: string = "16px";
  export let iconColor: string | undefined = undefined;
  export let iconHeight: string = "16px";

  $: buttonProps = {
    id,
    type,
    tabindex,
    disabled,
    ...$$restProps,
    class: [
      $$restProps.class,
      getButtonStyle({ size, variant, rounded, loading, fullWidth }),
    ]
      .filter(Boolean)
      .join(" "),
  };
</script>

<button bind:this={ref} {...buttonProps} on:click>
  <span class="flex items-center justify-center gap-2">
    {#if icon}
      <SvgIcon
        name={icon}
        width={iconWidth}
        height={iconHeight}
        color={iconColor}
      />
    {/if}

    {#if loading}
      <span
        class="h-4 w-4 animate-spin rounded-full border-2 border-slate-900 border-t-transparent"
      ></span>
    {/if}
    <slot />
  </span>
</button>
