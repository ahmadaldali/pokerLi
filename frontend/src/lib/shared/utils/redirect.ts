import { browser } from '$app/environment';
import { goto } from '$app/navigation';
import { redirect } from '@sveltejs/kit';

export function redirectTo(redirectUrl: string) {
    console.log('Redirecting to:', browser ? 'client-side' : 'server-side', redirectUrl);
    if (browser) {
        goto(redirectUrl, { replaceState: true });
    } else {
        throw redirect(302, redirectUrl);
    }
}