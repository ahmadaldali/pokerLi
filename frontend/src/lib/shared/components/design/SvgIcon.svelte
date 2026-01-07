<script lang="ts">
  import { icons, type iconName } from "$lib/shared/utils/ui/icon";

  export let name: iconName;
  export let color: string = "";
  export let bgColor: string = "";
  export let width = "32";
  export let height = "32";
  export let focusable: string | number | null | undefined = undefined;
  export let hasGradient: boolean = false;

  function addColorToSvg(svg: string): string {
    return svg
      .replaceAll(
        "__color__",
        hasGradient ? "url(#gradient)" : color || "currentColor"
      )
      .replaceAll("__bg-color__", bgColor || color || "currentColor");
  }

  $: displayIcon = icons[name];
</script>

<svg
  class={$$props.class}
  stroke-width={$$props.strokeWidth}
  fill={$$props.fill}
  {focusable}
  {width}
  {height}
  viewBox="0 0 {displayIcon.box}"
>
  {#if hasGradient}
    <slot name="gradient" />
  {/if}
  {#key bgColor}
    {@html addColorToSvg(displayIcon.svg)}
  {/key}
</svg>
