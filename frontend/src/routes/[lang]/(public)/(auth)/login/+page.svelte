<script lang="ts">
  import { superForm } from "sveltekit-superforms";
  import type { PageData } from "./$types";
  import Error from "$components/design/Error.svelte";
  import AuthContainer from "$components/form/AuthContainer.svelte";
  import LL from "$i18n/i18n-svelte";

  export let data: PageData;

  const { form, errors, enhance, message, submitting } = superForm(data.form, {
    resetForm: false,
  });
</script>

<AuthContainer
  title={$LL.pages.auth.login.title()}
  response={$message}
  link={$LL.routes.auth.signUp()}
  linkText={$LL.pages.auth.login.signUp()}
  linkTitle={$LL.pages.auth.login.noAccount()}
>
  <form method="POST" use:enhance class="space-y-5">
    <!-- Email -->
    <div>
      <label for="email" class="block text-sm font-medium text-slate-300 mb-1">
        Email
      </label>
      <input
        id="email"
        name="email"
        type="email"
        bind:value={$form.email}
        required
        class="w-full rounded-lg bg-slate-800 border border-slate-700 px-4 py-2.5 text-white placeholder-slate-500
                 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500
                 transition"
        placeholder="you@example.com"
      />
      <Error class="mt-2" error={$errors.email} />
    </div>

    <!-- Password -->
    <div>
      <label
        for="password"
        class="block text-sm font-medium text-slate-300 mb-1"
      >
        Password
      </label>
      <input
        id="password"
        name="password"
        type="password"
        bind:value={$form.password}
        required
        class="w-full rounded-lg bg-slate-800 border border-slate-700 px-4 py-2.5 text-white placeholder-slate-500
                 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500
                 transition"
        placeholder="••••••••"
      />
      <Error class="mt-2" error={$errors.password} showIcon={true} />
    </div>

    <!-- Submit -->
    <button
      type="submit"
      disabled={$submitting}
      class="relative w-full rounded-lg bg-emerald-500 hover:bg-emerald-400
               disabled:bg-emerald-700 disabled:cursor-not-allowed
               text-slate-950 font-semibold py-2.5
               transition focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:ring-offset-2 focus:ring-offset-slate-900"
    >
      {#if $submitting}
        <span class="flex items-center justify-center gap-2">
          <span
            class="h-4 w-4 animate-spin rounded-full border-2 border-slate-900 border-t-transparent"
          ></span>
          Logging in…
        </span>
      {:else}
        Login
      {/if}
    </button>
  </form>
</AuthContainer>
