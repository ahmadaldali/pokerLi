<script lang="ts">
  import { superForm } from "sveltekit-superforms";
  import type { PageData } from "./$types";
  import LL from "$i18n/i18n-svelte";
  import FormContainer from "$components/form/FormContainer.svelte";
  import Input from "$components/design/Input.svelte";
  import Button from "$components/design/Button.svelte";

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
      type="password"
      bind:value={$form.confirmPassword}
      label={$LL.fields.confirmPassword.label()}
      placeholder={$LL.fields.confirmPassword.placeholder()}
      required
      errors={$errors.confirmPassword}
    />

    <!-- Submit -->
    <Button type="submit" disabled={$submitting} loading={$submitting}>
      {$LL.pages.auth.signUp.submit()}
    </Button>
  </form>
</FormContainer>
