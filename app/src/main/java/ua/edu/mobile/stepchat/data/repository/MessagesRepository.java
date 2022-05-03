package ua.edu.mobile.stepchat.data.repository;

import java.util.HashMap;
import java.util.Map;

import ua.edu.mobile.stepchat.data.models.Message;
import ua.edu.mobile.stepchat.shared.fetching.FetchResultReceiver;

public class MessagesRepository extends RepositoryBase<Message> implements FetchResultReceiver<Message> {

    private Map<Integer, Message> messages = new HashMap<>();

    public MessagesRepository(RepositoryObserver<Message> observer) {
        super(observer);
    }

    public int count() {
        return messages.size();
    }

    @Override
    public void onFetchReceive(Message result) {
        if(messages.containsKey(result.getId())) {
            Message message = messages.get(result.getId());
            messages.put(result.getId(), result);
            getObserver().onUpdate(message, result);
        }else {
            messages.put(result.getId(), result);
            getObserver().onAdd(result);
        }
    }

    @Override
    public void onFetchFailed(String details) {
        getErrorHandler().onError(details);
    }
}
