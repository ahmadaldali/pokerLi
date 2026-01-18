<script lang="ts">
  import Input from "$components/design/Input.svelte";
  import { superForm } from "sveltekit-superforms";
  import LL from "$i18n/i18n-svelte";
  import Button from "$components/design/Button.svelte";
  import type { PageData } from "./$types";
  import FormContainer from "$components/form/FormContainer.svelte";

  export let data: PageData;

  const { form, errors, enhance, message, submitting } = superForm(data.form, {
    resetForm: false,
  });
</script>

<div class="w-full md:max-w-md">
  <FormContainer title="dsa" response={$message}>
    <form method="POST" use:enhance class="space-y-5">
      <Input
        name="name"
        type="text"
        bind:value={$form.name}
        label={$LL.fields.sprintName.label()}
        placeholder={$LL.fields.sprintName.placeholder()}
        required
        errors={$errors.name}
      />
      <Input
        name="sequence"
        type="text"
        bind:value={$form.sequence}
        label={$LL.fields.sequence.label()}
        placeholder={$LL.fields.sequence.placeholder()}
        required
        errors={$errors.sequence as unknown as string[]}
      />

      <Button type="submit" disabled={$submitting} loading={$submitting}>
        {$LL.pages.admin.sprints.new.submit()}
      </Button>
    </form>
  </FormContainer>
</div>
