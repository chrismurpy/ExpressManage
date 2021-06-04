package com.murphy.exception;

/**
 * 取件码重复异常
 *
 * @author murphy
 * @since 2021/6/4 3:37 下午
 */
public class DuplicateCodeException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public DuplicateCodeException() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DuplicateCodeException(String message) {
        super(message);
    }
}
