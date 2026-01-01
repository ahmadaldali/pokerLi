<script lang="ts">
  import { onMount, onDestroy } from 'svelte';
  import { connect, disconnect } from '$lib/websocket';
  import type { UserStoryDetails } from '$lib/websocket';

  let updates: UserStoryDetails[] = [];

  onMount(() => {
    connect((data) => {
      console.log('Received update:', data);
      updates = [data, ...updates];
    });
  });

  onDestroy(() => {
    disconnect();
  });
</script>

<h1>User Story Updates</h1>

{#if updates.length === 0}
  <p>Waiting for real-time updates…</p>
{:else}
  <ul>
    {#each updates as u (u.id)}
      <li>
        <strong>ID:</strong> {u.id}
        —
        <strong>Status:</strong> {u.status}
      </li>
    {/each}
  </ul>
{/if}
