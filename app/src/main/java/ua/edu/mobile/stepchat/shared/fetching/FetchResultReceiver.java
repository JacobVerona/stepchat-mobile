package ua.edu.mobile.stepchat.shared.fetching;

public interface FetchResultReceiver<T> {
    void onFetchReceive(T result);
    void onFetchFailed(String details);
}
