package ua.edu.mobile.stepchat.data.repository;

public interface RepositoryObserver<T> {
    void onAdd(T data);
    void onRemove(T data);
    void onUpdate(T oldData, T newData);
}
