<script lang="ts">
  import { superForm } from "sveltekit-superforms";
  import type { PageData } from "./$types";
  import FormContainer from "$components/form/FormContainer.svelte";
  import LL from "$i18n/i18n-svelte";
  import Input from "$components/design/Input.svelte";
  import Button from "$components/design/Button.svelte";

  export let data: PageData;

  const { form, errors, enhance, message, submitting } = superForm(data.form, {
    resetForm: false,
  });
</script>

<FormContainer
  title={$LL.pages.auth.login.title()}
  response={$message}
  link={$LL.routes.auth.signUp()}
  linkText={$LL.pages.auth.login.signUp()}
  linkTitle={$LL.pages.auth.login.noAccount()}
>
  <form method="POST" use:enhance class="space-y-5">
    <Input
      name="email"
      type="email"
      bind:value={$form.email}
      label={$LL.fields.email.label()}
      placeholder={$LL.fields.email.placeholder()}
      required
      errors={$errors.email}
    />

    <!-- Password -->
    <Input
      name="password"
      type="password"
      bind:value={$form.password}
      label={$LL.fields.password.label()}
      placeholder={$LL.fields.password.placeholder()}
      required
      errors={$errors.password}
    />

    <Button type="submit" disabled={$submitting} loading={$submitting}>
      {$LL.pages.auth.login.submit()}
    </Button>
  </form>
</FormContainer>
