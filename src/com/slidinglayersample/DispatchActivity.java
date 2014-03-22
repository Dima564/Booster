package com.slidinglayersample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author bamboo
 * @since 3/22/14 9:26 AM
 */
public class DispatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (User.getCurrentUser() == null) {
            startActivity(new Intent(this, RegisterOrSignInActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}
