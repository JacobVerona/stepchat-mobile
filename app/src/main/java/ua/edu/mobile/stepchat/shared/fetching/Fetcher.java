package ua.edu.mobile.stepchat.shared.fetching;

public abstract class Fetcher<T> {

    private FetchResultReceiver<T> receiver;

    public Fetcher(FetchResultReceiver<T> receiver) {
        this.receiver = receiver;
    }

    public FetchResultReceiver<T> getReceiver(){
        return receiver;
    }

    public abstract void fetch();
}
