<script lang="ts">
  import { superForm } from "sveltekit-superforms";
  import type { PageData } from "./$types";
  import Error from "$components/design/Error.svelte";
  import { getL18ErrorMessage } from "$lib/shared/api/http";
  import LL from "$i18n/i18n-svelte";
  import AuthContainer from "$components/form/AuthContainer.svelte";

  export let data: PageData;

  const { form, errors, enhance, message, submitting } = superForm(data.form, {
    resetForm: false,
  });
</script>

<AuthContainer
  title={$LL.pages.auth.signUp.title()}
  response={$message}
  link={$LL.routes.auth.login()}
  linkText={$LL.pages.auth.signUp.signIn()}
  linkTitle={$LL.pages.auth.signUp.haveAccount()}
>
  <form method="POST" use:enhance class="space-y-5">
    <!-- Name -->
    <div>
      <label for="name" class="block text-sm font-medium text-slate-300 mb-1">
        Name
      </label>
      <input
        id="name"
        name="name"
        type="text"
        bind:value={$form.name}
        required
        class="w-full rounded-lg bg-slate-800 border border-slate-700 px-4 py-2.5 text-white placeholder-slate-500
                 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500
                 transition"
        placeholder="John Doe"
      />
      <Error class="mt-2" error={$errors.name} />
    </div>

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

    <!-- Confirm Password -->
    <div>
      <label
        for="confirmPassword"
        class="block text-sm font-medium text-slate-300 mb-1"
      >
        Confirm password
      </label>
      <input
        id="confirmPassword"
        name="confirmPassword"
        type="password"
        bind:value={$form.confirmPassword}
        required
        class="w-full rounded-lg bg-slate-800 border border-slate-700 px-4 py-2.5 text-white placeholder-slate-500
                 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-emerald-500
                 transition"
        placeholder="••••••••"
      />
      <Error class="mt-2" error={$errors.confirmPassword} showIcon={true} />
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
          Creating account…
        </span>
      {:else}
        Sign up
      {/if}
    </button>
  </form>
</AuthContainer>
