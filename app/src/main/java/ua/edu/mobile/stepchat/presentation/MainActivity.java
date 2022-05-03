package ua.edu.mobile.stepchat.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.edu.mobile.stepchat.R;
import ua.edu.mobile.stepchat.shared.API;
import ua.edu.mobile.stepchat.data.api.StepChatAPI;
import ua.edu.mobile.stepchat.data.models.User;
import ua.edu.mobile.stepchat.features.login.Login;
import ua.edu.mobile.stepchat.features.login.OnLogin;
import ua.edu.mobile.stepchat.shared.Config;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 0;

    private Login login;

    @BindView(R.id.loginButton)
    Button signUpButton;

    private void initDependencies() {
        StepChatAPI api = API.getApiClient().create(StepChatAPI.class);
        login = new Login(api, API.getTokenRepository());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDependencies();
        initViewActions();
    }

    private void initViewActions() {
        signUpButton.setOnClickListener((view)->{
            startGoogleSignIn();
        });
    }

    private void startGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Config.SERVER_CLIENT_ID)
                .requestEmail()
                .requestProfile()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleResult(task);
        }
    }

    private void handleResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount acc = task.getResult(ApiException.class);
            login.login(acc.getIdToken(), new OnLogin() {
                @Override
                public void loginSuccess(User user) {
                    startActivity(new Intent(MainActivity.this, ChatActivity.class));
                }

                @Override
                public void loginFailed(String details) {
                    Log.e("Login", details);
                }
            });
        } catch (ApiException e) {
            Log.e("Login", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
