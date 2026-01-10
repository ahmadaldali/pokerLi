import pino from 'pino';
import { browser } from '$app/environment';

/** All available logger levels for the {@link Logger} service. */
export type LoggerLevel = 'debug' | 'info' | 'warn' | 'error';

/**
 * Logger service interface that can be used to log messages with optional
 * metadata across the application.
 */
export interface Logger {
    /**
     * Creates a new child logger instance with the given metadata.
     *
     * @param metadata
     * The metadata to add to the child logger.
     *
     * @returns
     * A new child logger instance.
     */
    child(metadata: Record<string, any>): Readonly<Logger>;

    /**
     * Logs a debug message with optional metadata.
     *
     * @param log
     * The message to log.
     *
     * @param metadata
     * Optional metadata to log.
     */
    debug(log: string, metadata?: Record<string, any>): void;

    /**
     * Logs an info message with optional metadata.
     *
     * @param log
     * The message to log.
     *
     * @param metadata
     * Optional metadata to log.
     */
    info(log: string, metadata?: Record<string, any>): void;

    /**
     * Logs a warning message with optional metadata.
     *
     * @param log
     * The message to log.
     *
     *  @param metadata
     * Optional metadata to log.
     */
    warn(log: string, metadata?: Record<string, any>): void;

    /**
     * Logs an error message with optional metadata.
     *
     * @param log
     * The message to log.
     *
     * @param metadata
     * Optional metadata to log.
     */
    error(log: string, metadata?: Record<string, any>): void;
}

/** Initializes the logger service. */
export default function getLogger(): Readonly<Logger> {
    return createLogger().child({ app: 'pokerLi' });
}

/** Creates a new logger instance. */
function createLogger(child?: pino.Logger): Readonly<Logger> {
    const logger = child ?? (browser ? clientLogger() : serverLogger());

    return {
        child(metadata: Record<string, any>) {
            return createLogger(logger.child(metadata));
        },
        debug(log: string, metadata: any) {
            logger.debug(metadata, log);
        },
        info(log: string, metadata: any) {
            logger.info(metadata, log);
        },
        warn(log: string, ...metadata: any[]) {
            logger.warn(metadata, log);
        },
        error(log: string, metadata: any) {
            logger.error(metadata, log);
        }
    };
}

/**
 * Creates a new logger instance for the server.
 *
 * @remarks
 * The server logger is configured to log to the console in development mode.
 *
 * @returns
 * A new logger instance.
 */
function serverLogger() {
    return pino({
        level: 'info',
        timestamp: pino.stdTimeFunctions.isoTime,
        formatters: {
            level: (label) => ({ level: label.toUpperCase() })
        }
    });
}

/**
 * Creates a new logger instance for the client.
 *
 * @remarks
 * The client logger is configured to log to the console in development mode.
 *
 * @returns
 * A new logger instance.
 */
function clientLogger() {
    return pino({
        level: 'info',
        timestamp: pino.stdTimeFunctions.isoTime,
        formatters: {
            level: (label) => ({ level: label.toUpperCase() })
        }
    });
}
