<script lang="ts">
  import { colors } from "$lib/shared/utils/ui/color";
  import SvgIcon from "./SvgIcon.svelte";

  export let error: string | null | string[] = null;
  export let showIcon: boolean = false;

  $: if (Array.isArray(error)) {
    error = error.join(", ");
  }
</script>

{#if error}
  <div
    class="flex w-full items-center justify-center rounded-lg bg-red-500/10 border border-red-500/20 px-4 py-3 text-sm text-red-400 {$$props.class}"
  >
    {#if showIcon}
      <SvgIcon
        class="mr-2"
        name="error"
        width="24"
        height="24"
        color={colors.red13}
      />
    {/if}
    <span class="">{error}</span>
  </div>
{/if}
