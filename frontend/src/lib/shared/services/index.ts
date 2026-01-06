import getLogger, { type Logger } from './logger';

/** Obtain a reference to the logger service. */
export let logger: Readonly<Logger>;

/** Initializes all services. */
export function initServices() {
    logger = getLogger();
}
