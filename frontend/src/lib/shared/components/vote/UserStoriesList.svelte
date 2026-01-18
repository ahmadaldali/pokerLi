<script lang="ts">
  import { userStoriesApi } from "$lib/shared/api/user-story";
  import type { TUserStory } from "$lib/shared/types/sprint";
  import UserStoryItem from "./UserStoryItem.svelte";

  export let userStories: TUserStory[] = [];
  export let activeVotingUserStory: TUserStory | null = null;

  async function selectUserStory(userStoryId: number) {
    await userStoriesApi().select(userStoryId);
  }

  async function voteAgain(userStoryId: number) {
    await userStoriesApi().voteAgain(userStoryId);
  }
</script>

<ul class="divide-y divide-white/5">
  {#each userStories as story (story.id)}
    <UserStoryItem
      id={story.id}
      name={story.name}
      description={story.description}
      active={activeVotingUserStory?.id === story.id}
      onVote={story.isRevealed ? voteAgain : selectUserStory}
      isRevealed={story.isRevealed}
    />
  {/each}
</ul>
