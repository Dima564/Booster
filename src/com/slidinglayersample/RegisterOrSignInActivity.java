package com.slidinglayersample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.github.johnpersano.supertoasts.SuperToast;

/**
 * @author bamboo
 * @since 3/22/14 9:28 AM
 */
public class RegisterOrSignInActivity extends Activity {


    public static final String KEY_NICKNAME = "oyster.nickname";
    public static final String KEY_PSSWORD = "oyster.nickname";
    private ProgressDialog dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // Log in button click handler
        ((Button) findViewById(R.id.btnSignIn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity

                final EditText editUserName = (EditText) findViewById(R.id.log_username);
                String nickname = editUserName.getText().toString();
                final EditText editPassword = (EditText) findViewById(R.id.log_password);
                String password = editPassword.getText().toString();

                User newUser = new User();
                newUser.setNickname(nickname);
                newUser.setPassword(password);


                dlg = new ProgressDialog(RegisterOrSignInActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Authorization.  Please wait.");
                dlg.show();

                new AuthorizeUserTask().execute(newUser);


            }
        });

        // Sign up button click handler
        ((Button) findViewById(R.id.btnRegister)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                Intent i = new Intent(RegisterOrSignInActivity.this, RegisterActivity.class);

                final EditText editUserName = (EditText) findViewById(R.id.log_username);
                String nickname = editUserName.getText().toString();
                final EditText editPassword = (EditText) findViewById(R.id.log_password);
                String password = editPassword.getText().toString();

                i.putExtra(KEY_NICKNAME, nickname);
                i.putExtra(KEY_PSSWORD, password);

                startActivity(i);
            }
        });
    }

    class AuthorizeUserTask extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... params) {

            Log.i("ololo", "  authorization  ");

            User user = params[0];
            String result = ServerFetcher.authorizeUser(user);

            user.setId(result);

            return user;
        }

        @Override
        protected void onPostExecute(User user) {

            if (dlg != null && dlg.isShowing()) {
                dlg.dismiss();
            }

            if (!user.getId().equals("-1")) {
                if (User.authorizeUser(user)) {

                    Log.i("ololo", "Successfully authorized ");

                    showToast("You are successfully authorized :)", SuperToast.Background.BLUE);

                    RegisterOrSignInActivity.this.finish();

                    startActivity(new Intent(RegisterOrSignInActivity.this, MainActivity.class));
                    return;
                }
                ;
            }

            showToast("Authorization failed, try again", SuperToast.Background.RED);

        }
    }

    void showToast(String text, int background) {
        SuperToast toast = new SuperToast(RegisterOrSignInActivity.this);

        toast.setText(text);
        toast.setAnimations(SuperToast.Animations.FADE);

        toast.setDuration(SuperToast.Duration.SHORT);
        toast.setBackground(background);
        toast.setTextSize(SuperToast.TextSize.MEDIUM);
        toast.setIcon(SuperToast.Icon.Light.INFO, SuperToast.IconPosition.LEFT);

    }
}