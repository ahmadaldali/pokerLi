<script lang="ts">
  import Error from "./Error.svelte";

  export let label;
  export let type = "text";
  export let name;
  export let placeholder = "";
  export let required = false;
  export let value: any;
  export let errors: string | string[] | null = null;

  function setType(node: any) {
    node.type = type;
  }
</script>

<div class="gap-y-2 flex flex-col">
  <div class={$$props.classWrapper}>
    <label for={name} class="block text-sm font-medium text-slate-300 mb-1">
      {label}
      {#if required}*{/if}
    </label>
    <input
      use:setType
      {name}
      {placeholder}
      bind:value
      class="w-full rounded-lg bg-slate-800 border border-slate-700 px-4 py-2.5 text-white placeholder-slate-500
                 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500
                 transition"
    />
  </div>
  <Error error={errors} />
</div>
