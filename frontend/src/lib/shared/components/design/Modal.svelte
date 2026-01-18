<script lang="ts">
  import type { TButtonType, TButtonVariant } from "$lib/shared/types/button";
  import { colors } from "$lib/shared/utils/ui/color";
  import Button from "./Button.svelte";

  export let open = false;
  export let title: string | undefined = undefined;

  export let onClose: (() => void) | null = null;

  export let confirmText: string | undefined = undefined;
  export let confirmVariant: TButtonVariant = "primary";
  export let confirmButtonType: TButtonType = "button";

  export let cancelText: string | undefined = undefined;
  export let cancelVariant: TButtonVariant = "outline";

  export let onConfirm: (() => void) | null = null;
  export let onCancel: (() => void) | null = null;

  let modalEl: HTMLDivElement;

  function close() {
    open = false;
    onClose?.();
  }

  function handleKeydown(e: KeyboardEvent) {
    if (e.key === "Escape") close();
  }

  function handleKeydownDialog(e: KeyboardEvent) {
    if (e.key === "Escape") {
      close();
    }
    if (e.key === "Enter" || e.key === " ") {
      // Only close if the dialog itself is focused and Enter/Space is pressed
      if (e.currentTarget === document.activeElement) {
        close();
      }
    }
  }

  function clickOutside(e: MouseEvent) {
    if (!modalEl.contains(e.target as Node)) {
      close();
    }
  }

  function handleDialogClick(e: MouseEvent) {
    // This is needed to satisfy a11y: click events must have key events, but we handle key events above
    // No-op, just to satisfy the rule
  }
</script>

<svelte:window on:keydown={handleKeydown} />

{#if open}
  <div
    class="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm px-4"
    on:click={clickOutside}
    role="presentation"
  >
    <div
      bind:this={modalEl}
      on:click|stopPropagation
      role="dialog"
      aria-modal="true"
      tabindex="0"
      on:keydown={handleKeydownDialog}
      on:click={handleDialogClick}
      class="
        w-full max-w-lg rounded-2xl
        border border-emerald-500/20
        bg-slate-900 shadow-xl shadow-emerald-500/10
      "
    >
      <!-- HEADER -->
      {#if title}
        <div
          class="border-b border-slate-800 px-6 py-4 flex shrink-0 items-center justify-between"
        >
          <div class="font-primary-medium flex items-center gap-x-2 text-xl">
            <h2 class="text-lg font-semibold text-slate-100">
              {title}
            </h2>
          </div>
          <Button
            icon="close"
            iconColor={colors.gray01}
            variant="none"
            size="sm"
            fullWidth={false}
            on:click={() => {
              open = false;
              close?.();
            }}
          />
        </div>
      {/if}

      <!-- BODY -->
      <div class="max-h-[70vh] overflow-y-auto px-6 py-5">
        <slot />
      </div>

      <!-- ACTIONS -->
      {#if confirmText || cancelText}
        <div class="flex justify-end gap-3 border-t border-slate-800 px-6 py-4">
          {#if cancelText}
            <Button
              variant={cancelVariant}
              on:click={() => {
                onCancel?.();
                close();
              }}
            >
              {cancelText}
            </Button>
          {/if}

          {#if confirmText}
            <Button
              type={confirmButtonType}
              variant={confirmVariant}
              on:click={() => {
                onConfirm?.();
              }}
            >
              {confirmText}
            </Button>
          {/if}
        </div>
      {/if}
    </div>
  </div>
{/if}
