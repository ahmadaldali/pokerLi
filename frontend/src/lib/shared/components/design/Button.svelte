<script lang="ts">
	export let type: string = 'button';
	export let disabled = false;
</script>

<button {disabled}
	{type}
	class="{disabled == true
	? 'bg-gray-100 hover:bg-grey-700'
	: 'bg-primary hover:bg-primary-700 focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500'} inline-flex items-center px-4 py-2 border border-transparent text-base font-medium rounded-md shadow-sm text-white focus:outline-none"
 >
	<slot />
</button>