<script>
  import Button from "$components/design/Button.svelte";
  import Input from "$components/design/Input.svelte";
  import { LL } from "$i18n/i18n-svelte";
  import { getL18ErrorMessage } from "$lib/shared/api/http.js";

  let email = "";
  let password = "";

  export let form;
</script>

<form method="post" class={$$props.class}>
  <Input
    label="Email"
    id="email"
    name="email"
    type="email"
    bind:value={email}
    required
  />

  <Input
    label="Password"
    id="password"
    name="password"
    type="password"
    bind:value={password}
    required
  />

  <Button type="submit">Login</Button>
</form>

{#if form?.response.success === false}
  <div class="mt-3 text-red-500 text-center font-semibold">
    {getL18ErrorMessage($LL.errors, form.response.result.error)}
  </div>
{/if}
