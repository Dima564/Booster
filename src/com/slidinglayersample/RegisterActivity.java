package com.slidinglayersample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.github.johnpersano.supertoasts.SuperToast;

/**
 * @author bamboo
 * @since 3/22/14 9:40 AM
 */
public class RegisterActivity extends Activity {

    private EditText mEditNickname;
    private EditText mEditFullname;
    private EditText mEditPassword;
    private EditText mEditEmail;
    private EditText mEditPhone;
    private EditText mEditDateOfBirth;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);


        mEditNickname = (EditText) findViewById(R.id.reg_nickname);
        mEditFullname = (EditText) findViewById(R.id.reg_full_name);
        mEditPassword = (EditText) findViewById(R.id.reg_password);
        mEditEmail = (EditText) findViewById(R.id.reg_email);
        mEditPhone = (EditText) findViewById(R.id.reg_phone);
        mEditDateOfBirth = (EditText) findViewById(R.id.reg_birth_date);

        mEditNickname.setText(getIntent().getStringExtra(RegisterOrSignInActivity.KEY_NICKNAME));

        mEditPassword.setText(getIntent().getStringExtra(RegisterOrSignInActivity.KEY_PSSWORD));

        final Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nickname = mEditNickname.getText().toString();
                String fullname = mEditFullname.getText().toString();
                String password = mEditPassword.getText().toString();
                String email = mEditEmail.getText().toString();
                String phone = mEditPhone.getText().toString();
                String dateOfBirth = mEditDateOfBirth.getText().toString();

                User user = new User("1", fullname, nickname, email, password);
                if (!isEmpty(phone))
                    user.setPhoneNumber(phone);
                if (!isEmpty(dateOfBirth))
                    user.setDateOfBirth(dateOfBirth);


                if (isEmpty(nickname)
                        | isEmpty(fullname)
                        | isEmpty(password)
                        | isEmpty(email)) {

                    SuperToast toast = new SuperToast(RegisterActivity.this);

                    toast.setText("Provide full info !");
                    toast.setAnimations(SuperToast.Animations.FADE);

                    toast.setDuration(SuperToast.Duration.SHORT);
                    toast.setBackground(SuperToast.Background.GREEN);
                    toast.setTextSize(SuperToast.TextSize.MEDIUM);
                    toast.setIcon(SuperToast.Icon.Light.INFO, SuperToast.IconPosition.LEFT);


                    toast.show();

                    return;
                } else {
                    User.authorizeUser(user);
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
            }
        });

    }

    public boolean isEmpty(String s) {
        return s.trim().length() == 0;
    }
}