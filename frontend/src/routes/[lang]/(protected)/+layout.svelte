<script lang="ts">
  let { children } = $props();
</script>

  <div
    class="w-full rounded-2xl bg-slate-900/80 backdrop-blur border border-white/10 shadow-2xl p-8 m-20"
  >
{@render children()}
</div>