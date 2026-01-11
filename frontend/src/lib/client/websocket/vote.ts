import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import type { TSprint, TUserStory } from "$lib/shared/types/sprint";

let client: Client | null = null;

export function connect(
  onUserStory: (data: TUserStory) => void,
  onSprint: (data: TSprint) => void
): void {
  client = new Client({
    webSocketFactory: () => new SockJS("http://localhost:8000/api/ws"),

    reconnectDelay: 5000,

    onConnect: () => {
      console.log("✅ WebSocket connected");

      // User story events
      client?.subscribe("/topic/user-story-channel", (message) => {
        console.log("📩 User story message received", message);
        const payload = JSON.parse(message.body) as {
          user_story: TUserStory;
        };
        onUserStory(payload.user_story);
      });

      // Sprint events
      client?.subscribe("/topic/sprint-channel", (message) => {
        console.log("📩 Sprint message received", message);
        const payload = JSON.parse(message.body) as {
          sprint: TSprint;
        };
        onSprint(payload.sprint);
      });
    },

    debug: () => {},
  });

  client.activate();
}

export function disconnect(): void {
  client?.deactivate();
  client = null;
}
