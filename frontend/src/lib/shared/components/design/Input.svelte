<script lang="ts">

	export let value: any;
	export let id;
	export let label;
	export let type = "text";
	export let name;
	export let placeholder = "";
	export let required = false;

	export let inputRef = null;

	function setType(node: any) {
		node.type = type;
	}
</script>

<div class={$$props.classWrapper}>
	<label for={id} class="block text-sm font-medium text-gray-700 mb-2">
		{label}:
	</label>
	<input
		use:setType
		{name}
		id={id}
		{required}
		placeholder={placeholder}
		bind:value
		bind:this={inputRef}
		class="form-control outline-none"
	/>
</div>