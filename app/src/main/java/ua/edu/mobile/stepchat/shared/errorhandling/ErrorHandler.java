package ua.edu.mobile.stepchat.shared.errorhandling;

public interface ErrorHandler<T> {
    void onError(T error);
}
