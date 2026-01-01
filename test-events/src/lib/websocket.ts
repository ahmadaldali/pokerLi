import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

/**
 * DTO coming from Spring Boot
 */
export interface UserStoryDetails {
  id: number;
  status: string;
}

let client: Client | null = null;

export function connect(
  onMessage: (data: UserStoryDetails) => void
): void {
  client = new Client({
    webSocketFactory: () =>
      new SockJS('http://localhost:8000/api/ws'),

    reconnectDelay: 5000,

    onConnect: () => {
      console.log('✅ WebSocket connected');

      client?.subscribe(
        '/topic/user-story-channel',
        (message: any) => {
          const payload = JSON.parse(message.body) as {
            user_story_details: UserStoryDetails;
          };

          onMessage(payload.user_story_details);
        }
      );
    },

    debug: () => {}
  });

  client.activate();
}

export function disconnect(): void {
  client?.deactivate();
  client = null;
}
