<script lang="ts">
  import type { TButtonSize, TButtonType } from "$lib/shared/types/button";
  import { generateRandomString } from "$lib/shared/utils/helper";
  import { getButtonStyle } from "$lib/shared/utils/ui/button";

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

  $: buttonProps = {
    id,
    type,
    tabindex,
    disabled,
    ...$$restProps,
    class: [$$restProps.class, getButtonStyle(size, rounded, loading, fullWidth)]
      .filter(Boolean)
      .join(" "),
  };
</script>

<button bind:this={ref} {...buttonProps} on:click>
  <span class="flex items-center justify-center gap-2">
    {#if loading}
      <span
        class="h-4 w-4 animate-spin rounded-full border-2 border-slate-900 border-t-transparent"
      ></span>
    {/if}
    <slot />
  </span>
</button>
