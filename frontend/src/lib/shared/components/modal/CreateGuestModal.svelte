<script lang="ts">
  import Input from "$components/design/Input.svelte";
  import Modal from "$components/design/Modal.svelte";
  import { superForm, type SuperValidated } from "sveltekit-superforms";
  import LL from "$i18n/i18n-svelte";
  import Error from "$components/design/Error.svelte";
  import { getL18ErrorMessage } from "$lib/shared/api";
  import Button from "$components/design/Button.svelte";
  import Link from "$components/design/Link.svelte";

  export let formData: SuperValidated<
    {
      name: string;
      guestId: string;
    },
    App.Superforms.Message,
    {
      name: string;
      guestId: string;
    }
  >;

  const { form, errors, enhance, message, submitting } = superForm(formData, {
    resetForm: false,
  });
</script>

<Modal title="Unauthorized Access" open={true}>
  <form method="POST" class="space-y-5" action="?/createGuest">
    <div class="mx-auto max-w-md space-y-6">
      <p class="text-lg text-center font-semibold text-white">
        Choose your display name
      </p>

      <div>
        <Input
          name="name"
          type="name"
          bind:value={$form.name}
          label={$LL.fields.name.label()}
          placeholder={$LL.fields.name.placeholder()}
          required
          errors={$errors.name}
        />
      </div>

      <!-- API Error -->
      {#if !$message?.success}
        <Error
          class="mb-6"
          error={getL18ErrorMessage($LL.errors, $message?.result?.error)}
        />
      {/if}

      <Button
        type="submit"
        variant="primary"
        loading={$submitting}
        disabled={$submitting}>"Join as a guest"</Button
      >

      <div
        class="rounded-xl text-center border border-white/10 bg-white/5 p-5 space-y-4"
      >
        <p class="text-sm text-white/70">Want to stay with us permanently?</p>

        <div class="flex items-center justify-between gap-4">
          <Link link={$LL.routes.auth.login()} linkText="Login" />

          <Link link={$LL.routes.auth.signUp()} linkText="Sign up" />
        </div>
      </div>
    </div>
  </form>
</Modal>
