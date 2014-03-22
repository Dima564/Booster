package com.slidinglayersample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author bamboo
 * @since 3/22/14 9:28 AM
 */
public class RegisterOrSignInActivity extends Activity {


    public static final String KEY_NICKNAME = "oyster.nickname";
    public static final String KEY_PSSWORD = "oyster.nickname";

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

                User.authorizeUser(newUser);

                startActivity(new Intent(RegisterOrSignInActivity.this, MainActivity.class));
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
}