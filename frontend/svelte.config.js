import adapterNode from '@sveltejs/adapter-node';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';
import path from 'path';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	// Consult https://svelte.dev/docs/kit/integrations
	// for more information about preprocessors
	preprocess: vitePreprocess(),

	kit: {
		// adapter-auto only supports some environments, see https://svelte.dev/docs/kit/adapter-auto for a list.
		// If your environment is not supported, or you settled on a specific environment, switch out the adapter.
		// See https://svelte.dev/docs/kit/adapters for more information about adapters.
		adapter: adapterNode(),
			alias: {
			'$components': path.resolve('./src/lib/shared/components'),
			'$components/*': path.resolve('./src/lib/shared/components/*'),
			'$routes': path.resolve('./src/routes'),
			'$routes/*': path.resolve('././src/routes/*'),
			'$i18n': path.resolve('././src/i18n'),
			'$i18n/*': path.resolve('./src/i18n/*'),
		},
		env: {
			dir: '../..'
		}
	}
};

export default config;
