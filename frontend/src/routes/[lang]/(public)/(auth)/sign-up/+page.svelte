<script lang="ts">
  import { superForm } from "sveltekit-superforms";
  import type { PageData } from "./$types";
  import LL from "$i18n/i18n-svelte";
  import FormContainer from "$components/form/FormContainer.svelte";
  import Input from "$components/design/Input.svelte";

  export let data: PageData;

  const { form, errors, enhance, message, submitting } = superForm(data.form, {
    resetForm: false,
  });
</script>

<FormContainer
  title={$LL.pages.auth.signUp.title()}
  response={$message}
  link={$LL.routes.auth.login()}
  linkText={$LL.pages.auth.signUp.signIn()}
  linkTitle={$LL.pages.auth.signUp.haveAccount()}
>
  <form method="POST" use:enhance class="space-y-5">
    <Input
      name="name"
      type="name"
      bind:value={$form.name}
      label={$LL.fields.name.label()}
      placeholder={$LL.fields.name.placeholder()}
      required
      errors={$errors.name}
    />

    <Input
      name="email"
      type="email"
      bind:value={$form.email}
      label={$LL.fields.email.label()}
      placeholder={$LL.fields.email.placeholder()}
      required
      errors={$errors.email}
    />

    <Input
      name="password"
      type="password"
      bind:value={$form.password}
      label={$LL.fields.password.label()}
      placeholder={$LL.fields.password.placeholder()}
      required
      errors={$errors.password}
    />

    <Input
      name="confirmPassword"
      type="confirmPassword"
      bind:value={$form.confirmPassword}
      label={$LL.fields.confirmPassword.label()}
      placeholder={$LL.fields.confirmPassword.placeholder()}
      required
      errors={$errors.confirmPassword}
    />

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
</FormContainer>
