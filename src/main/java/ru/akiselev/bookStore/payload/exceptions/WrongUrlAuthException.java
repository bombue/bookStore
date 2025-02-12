package ru.akiselev.bookStore.payload.exceptions;

public class WrongUrlAuthException extends RuntimeException {
    public WrongUrlAuthException(String generatedUrl) {
        super(String.format("User not found for registration url: %s", generatedUrl));
    }

    public WrongUrlAuthException(String generatedUrl, String username) {
        super(String.format("Wrong registration url %s for user %s", generatedUrl, username));
    }
}
