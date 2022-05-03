package ua.edu.mobile.stepchat.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.edu.mobile.stepchat.R;
import ua.edu.mobile.stepchat.data.models.Message;
import ua.edu.mobile.stepchat.data.models.User;
import ua.edu.mobile.stepchat.data.repository.UsersRepository;
import ua.edu.mobile.stepchat.features.users.Users;
import ua.edu.mobile.stepchat.shared.Config;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private Context context;
    private UsersRepository usersRepository;
    private Users usersSystem;

    private ArrayList<Message> messages;

    public MessagesAdapter(Context context, UsersRepository usersRepository, Users usersSystem, ArrayList<Message> messages) {
        this.context = context;
        this.usersRepository = usersRepository;
        this.usersSystem = usersSystem;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        User user = usersRepository.getUser(message.getUserId());

        if(user == null){
            usersSystem.getUser(message.getUserId(), (foundUser)->{
                holder.init(message, foundUser);
            });
        }else{
            holder.init(message, user);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.email)
        TextView email;

        @BindView(R.id.content)
        TextView content;

        @BindView(R.id.sendTime)
        TextView sendTime;

        @BindView(R.id.avatar)
        ImageView avatar;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void init(Message message, User user) {
            if(user == null) {
                user = new User("", "Unknown user", "-", "", 0);
            }

            name.setText(user.getName());
            email.setText(user.getEmail());
            content.setText(message.getContent());
            sendTime.setText(Config.DATEFORMAT.format(new Date(message.getSendTime())));
            Glide.with(context).load(user.getAvatarURL()).into(avatar);
        }
    }
}
