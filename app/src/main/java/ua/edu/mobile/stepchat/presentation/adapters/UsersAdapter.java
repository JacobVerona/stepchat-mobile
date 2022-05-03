package ua.edu.mobile.stepchat.presentation.adapters;

import android.content.Context;
import android.graphics.Color;
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
import ua.edu.mobile.stepchat.data.models.User;
import ua.edu.mobile.stepchat.shared.Config;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<User> users;

    public UsersAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UsersAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UsersAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.init(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.email)
        TextView email;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.avatar)
        ImageView avatar;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void init(User user) {
            name.setText(user.getName());
            email.setText(user.getEmail());

            if(user.isActive()) {
                status.setText("(online)");
                status.setTextColor(Color.GREEN);
            }else{
                status.setText("last seen: \n" + Config.DATEFORMAT.format(new Date(user.getLastFetchTime())));
                status.setTextColor(Color.RED);
            }

            Glide.with(context).load(user.getAvatarURL()).into(avatar);
        }
    }

}
