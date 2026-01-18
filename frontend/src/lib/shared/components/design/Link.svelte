<script lang="ts">
    export let link: string;
    export let linkText: string;
    export let linkTitle: string = "";
</script>

<p class="mt-6 text-center text-sm text-slate-400">
    {linkTitle}
    <a
      href={link}
      class="ml-1 font-medium text-emerald-400 hover:text-emerald-300 underline-offset-4 hover:underline transition"
    >
      {linkText}
    </a>
  </p>