package ua.edu.mobile.stepchat.shared.fetching;

public abstract class ThreadFetcher<T> extends Fetcher<T> {

    private int fetchPeriod;
    private Thread thread;
    private boolean started;

    public ThreadFetcher(int fetchPeriod, FetchResultReceiver<T> receiver) {
        super(receiver);
        this.fetchPeriod = fetchPeriod;

        thread = new Thread(this::threadWork);
        started = false;
    }

    public synchronized void startFetching() {
        if(!started){
            thread.start();
        }
        started = true;
    }

    public synchronized void stopFetching() {
        started = false;
    }

    private void threadWork() {
        while (started) {
            fetch();
            try {
                Thread.sleep(fetchPeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
