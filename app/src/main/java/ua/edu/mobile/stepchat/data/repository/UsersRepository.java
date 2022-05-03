package ua.edu.mobile.stepchat.data.repository;

import java.util.HashMap;
import java.util.Map;

import ua.edu.mobile.stepchat.data.models.User;
import ua.edu.mobile.stepchat.shared.fetching.FetchResultReceiver;

public class UsersRepository extends RepositoryBase<User> implements FetchResultReceiver<User[]> {

    private Map<String, User> users = new HashMap();

    public UsersRepository(RepositoryObserver<User> observer) {
        super(observer);
    }

    public User getUser(String userID) {
        return users.get(userID);
    }

    public int count() {
        return users.size();
    }

    @Override
    public void onFetchReceive(User[] result) {
        for (User user : result) {
            if(users.containsKey(user.getId())) {
                User oldUser = users.get(user.getId());
                users.put(user.getId(), user);
                getObserver().onUpdate(oldUser, user);
            }else{
                users.put(user.getId(), user);
                getObserver().onAdd(user);
            }
        }
    }

    @Override
    public void onFetchFailed(String details) {
        getErrorHandler().onError(details);
    }
}
