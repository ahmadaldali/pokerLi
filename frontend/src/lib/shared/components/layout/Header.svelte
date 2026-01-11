<script lang="ts">
  import PokerLiLogo from "$components/design/PokerLiLogo.svelte";
  import type { TUser } from "$lib/shared/types/user";
  import LL from "$i18n/i18n-svelte";

  export let user: TUser | null;

  let userMenuOpen = false;
</script>

<header class="fixed inset-x-0 top-0 z-50">
  <nav class="border-b border-white/10 bg-slate-900/80 backdrop-blur ">
    <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
      <div class="flex h-16 items-center justify-between">
        <!-- Logo -->
        <a href={"HOME_PAGE"} class="flex items-center gap-2">
          <PokerLiLogo />
        </a>

        <!-- Desktop nav -->
        <div class="flex items-center gap-6 text-sm">
          <a href={$LL.routes.sprints.index()} class="nav-link">{$LL.blocks.header.sprints()}</a>
          <a href={"PRICING"} class="nav-link">Pricing</a>
          <a href={"COMPANY"} class="nav-link">Company</a>
          <a href={"CONTACT"} class="nav-link">Contact</a>

          {#if user}
            <!-- User dropdown -->
            <div class="relative">
              <button
                on:click={() => (userMenuOpen = !userMenuOpen)}
                class="flex items-center gap-2 rounded-lg bg-slate-800 px-3 py-1.5 text-white hover:bg-slate-700 transition"
              >
                {user.name}
                <svg
                  class="h-4 w-4 opacity-70"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                >
                  <path
                    fill-rule="evenodd"
                    d="M5.23 7.21a.75.75 0 011.06.02L10 11.584l3.71-4.353a.75.75 0 111.14.976l-4.25 5a.75.75 0 01-1.14 0l-4.25-5a.75.75 0 01.02-1.06z"
                    clip-rule="evenodd"
                  />
                </svg>
              </button>

              {#if userMenuOpen}
                <div
                  class="absolute right-0 mt-2 w-44 rounded-xl border border-white/10 bg-slate-800 shadow-xl"
                >
                  <a href={"PROFILE"} class="dropdown-item">Profile</a>
                  <a href={$LL.routes.auth.logout()} class="dropdown-item text-red-400">
                    Logout
                  </a>
                </div>
              {/if}
            </div>
          {:else}
            <!-- Public actions -->
            <a href={$LL.routes.auth.login()} class="nav-link">Login</a>
            <a
              href={$LL.routes.auth.signUp()}
              class="rounded-lg bg-emerald-500 px-4 py-1.5 font-semibold text-slate-950 hover:bg-emerald-400 transition"
            >
              Get started
            </a>
          {/if}
        </div>
      </div>
    </div>
  </nav>
</header>

<style>
  .nav-link {
    @apply text-slate-300 hover:text-emerald-400 transition;
  }

  .dropdown-item {
    @apply block px-4 py-2 text-sm text-slate-200 hover:bg-slate-700 transition;
  }

  .mobile-link {
    @apply block rounded-lg px-3 py-2 text-slate-200 hover:bg-slate-800 transition;
  }
</style>
