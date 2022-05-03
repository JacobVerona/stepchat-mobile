package ua.edu.mobile.stepchat.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.edu.mobile.stepchat.R;
import ua.edu.mobile.stepchat.data.models.User;
import ua.edu.mobile.stepchat.data.repository.MessagesRepository;
import ua.edu.mobile.stepchat.data.repository.RepositoryObserver;
import ua.edu.mobile.stepchat.data.repository.UsersRepository;
import ua.edu.mobile.stepchat.presentation.adapters.UsersAdapter;
import ua.edu.mobile.stepchat.shared.API;
import ua.edu.mobile.stepchat.data.api.StepChatAPI;
import ua.edu.mobile.stepchat.data.models.Message;
import ua.edu.mobile.stepchat.data.models.SendMessage;
import ua.edu.mobile.stepchat.features.messages.Messages;
import ua.edu.mobile.stepchat.features.users.Users;
import ua.edu.mobile.stepchat.presentation.adapters.MessagesAdapter;
import ua.edu.mobile.stepchat.shared.Config;
import ua.edu.mobile.stepchat.shared.errorhandling.ErrorHandler;
import ua.edu.mobile.stepchat.shared.errorhandling.LogCastErrorHandler;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.userAvatar)
    ImageView avatar;

    @BindView(R.id.messageText)
    EditText messageText;

    @BindView(R.id.usersOnline)
    TextView usersOnlineText;

    @BindView(R.id.sendMessageButton)
    Button sendMessageButton;

    @BindView(R.id.closeUsersList)
    Button closeUsersListButton;

    private Messages messagesSystem;
    private Users usersSystem;

    private MessagesRepository messagesRepository;
    private UsersRepository usersRepository;

    private MessagesAdapter messagesAdapter;
    private UsersAdapter usersAdapter;

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();

    protected void initDependencies() {
        StepChatAPI api = API.getApiClient().create(StepChatAPI.class);
        messagesRepository = new MessagesRepository(new RepositoryObserver<Message>() {
            @Override
            public void onAdd(Message data) {
                messages.add(data);
                runOnUiThread(()->{
                    messagesAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onRemove(Message data) {
                messages.remove(data);
                runOnUiThread(()->{
                    messagesAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onUpdate(Message oldData, Message newData) {
                runOnUiThread(()->{
                    messagesAdapter.notifyDataSetChanged();
                });
            }
        });

        usersRepository = new UsersRepository(new RepositoryObserver<User>() {
            @Override
            public void onAdd(User data) {
                users.add(data);

                updateUsersOnlineCount();
                runOnUiThread(()->{
                    usersAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onRemove(User data) {
                users.remove(data);

                updateUsersOnlineCount();
                runOnUiThread(()->{
                    usersAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onUpdate(User oldData, User newData) {
                users.remove(oldData);
                users.add(newData);

                updateUsersOnlineCount();
                runOnUiThread(()->{
                    usersAdapter.notifyDataSetChanged();
                });
            }
        });

        ErrorHandler handler = new LogCastErrorHandler();
        usersRepository.setErrorHandler(handler);
        messagesRepository.setErrorHandler(handler);

        messagesSystem = new Messages(api, API.getTokenRepository(), Config.FETCH_MESSAGES_MS, messagesRepository);
        usersSystem = new Users(api, API.getTokenRepository(), Config.FETCH_USERS_MS, usersRepository);
    }

    protected void initViews() {
        progressBar.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messagesAdapter = new MessagesAdapter(this, usersRepository, usersSystem, messages);
        usersAdapter = new UsersAdapter(this, users);

        recyclerView.setAdapter(messagesAdapter);

        sendMessageButton.setOnClickListener((view)->{
            String text = messageText.getText().toString();
            if(text.isEmpty()) {
                return;
            }

            messageText.setText("");
            messagesSystem.sendMessage(new SendMessage(text));
        });

        usersOnlineText.setOnClickListener((view)->{
            recyclerView.setAdapter(usersAdapter);

            closeUsersListButton.setVisibility(View.VISIBLE);
            usersOnlineText.setVisibility(View.GONE);
        });

        closeUsersListButton.setOnClickListener((view) ->{
            recyclerView.setAdapter(messagesAdapter);

            usersOnlineText.setVisibility(View.VISIBLE);
            closeUsersListButton.setVisibility(View.GONE);
        });

        Glide.with(this).load(API.getTokenRepository().getLocalUser().getAvatarURL()).into(avatar);
        Log.e("User", API.getTokenRepository().getLocalUser().getAvatarURL());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        initDependencies();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        messagesSystem.startFetching();
        usersSystem.startFetching();
    }

    @Override
    protected void onPause() {
        super.onPause();
        messagesSystem.stopFetching();
        usersSystem.stopFetching();
    }

    private void updateUsersOnlineCount() {
        int usersCount = (int)users.stream().filter(user -> { return user.isActive(); }).count();
        runOnUiThread(()->{
            usersOnlineText.setText("Online Users (" + usersCount +")");
        });
    }
}
