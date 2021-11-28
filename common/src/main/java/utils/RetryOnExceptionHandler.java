package utils;

import exceptions.ClientNotReachableException;

import java.io.NotActiveException;

public class RetryOnExceptionHandler {
    public static final int DEFAULT_RETRIES = 30;
    public static final long DEFAULT_TIME_TO_WAIT_MS = 2000;

    private int numRetries;
    private long timeToWaitMS;

    public RetryOnExceptionHandler(int _numRetries,
                                   long _timeToWaitMS) {
        numRetries = _numRetries;
        timeToWaitMS = _timeToWaitMS;
    }

    public RetryOnExceptionHandler() {
        this(DEFAULT_RETRIES, DEFAULT_TIME_TO_WAIT_MS);
    }

    public boolean shouldRetry() {
        return (numRetries >= 0);
    }

    public void waitUntilNextTry() {
        try {
            Thread.sleep(timeToWaitMS);
        }
        catch (InterruptedException iex) { }
    }

    public void exceptionOccurred() throws ClientNotReachableException {
        numRetries--;
        if(!shouldRetry()) {
            throw new ClientNotReachableException("Retry limit exceeded.");
        }
        waitUntilNextTry();
    }
}
