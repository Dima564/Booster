/*
 * MainActivity.java
 * 
 * Copyright (C) 2013 6 Wunderkinder GmbH.
 * 
 * @author      Jose L Ugia - @Jl_Ugia
 * @author      Antonio Consuegra - @aconsuegra
 * @author      Cesar Valiente - @CesarValiente
 * @author      Benedikt Lehnert - @blehnert
 * @author      Timothy Achumba - @iam_timm
 * @version     1.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.slidinglayersample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.*;
import android.widget.*;
import com.github.johnpersano.supertoasts.SuperToast;
import com.slidinglayer.SlidingLayer;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private SlidingLayer mSlidingLayer;

    private TextView swipeText;
    private String mStickContainerToRightLeftOrMiddle;
    private boolean mShowShadow;
    private boolean mShowOffset;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private FrameLayout mFrameLayout;
    private LinearLayout mLinearLayout;

    ListView mListViewCompanies;
    ArrayList<Company> mCompanyArrayList = new ArrayList<Company>();
    CompanyAdapter mCompanyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

//        getPrefs();
        bindViews();
        initState();


        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = new MainFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.content_frame, fragment)
                    .commit();
        }
    }

    public void initState() {

        mListViewCompanies = (ListView) findViewById(R.id.listViewCompany);

        for (int i = 0; i < 10; i++) {
            Company c = new Company();
            c.setName("Company Name");
            mCompanyArrayList.add(c);
        }

        mCompanyAdapter = new CompanyAdapter(mCompanyArrayList);
        mListViewCompanies.setAdapter(mCompanyAdapter);

        mListViewCompanies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this, CompanyProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CompanyProfileActivity.KEY_COMPANY, mCompanyAdapter.getItem(position));
                i.putExtras(bundle);

                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        menu.findItem(R.id.action_sign_out).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                User.signOutUser();
                MainActivity.this.finish();
                startActivity(new Intent(MainActivity.this, RegisterOrSignInActivity.class));
                return true;
            }
        });


        menu.findItem(R.id.action_new_company).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final ArrayList<Integer> mSelectedItems = new ArrayList<Integer>();

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View v = inflater.inflate(R.layout.dialog_create_company, null, false);
                builder.setView(v);

                builder.setTitle("Add company");


                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Company c = new Company();

                        EditText name = (EditText) v.findViewById(R.id.reg_company_name);
                        EditText desc = (EditText) v.findViewById(R.id.reg_company_description);


                        c.setName(name.getText().toString());
                        c.setDescription(desc.getText().toString());
                        c.setCreatorId(User.getCurrentUser().getId());

                        mCompanyArrayList.add(c);
                        mCompanyAdapter.notifyDataSetChanged();

                        showToast("Added company : " + c.getName(), SuperToast.Background.BLUE);
//                        Toast.makeText(getApplicationContext(), mSelectedItems.size(), Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

        return true;
    }

   /* @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            getActionBar().setDisplayHomeAsUpEnabled(true);
//        }
    }  */


    private void bindViews() {
//        mSlidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer1);

        mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mLinearLayout = (LinearLayout) findViewById(R.id.drawer_linear_layout);

        mTitle = getResources().getString(R.string.app_name);
        mDrawerTitle = getResources().getString(R.string.app_name_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

//    /* Called whenever we call invalidateOptionsMenu() */
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mLinearLayout);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }

         /*
    private void getPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mStickContainerToRightLeftOrMiddle = prefs.getString("layer_location", "right");
        mShowShadow = prefs.getBoolean("layer_has_shadow", false);
        mShowOffset = prefs.getBoolean("layer_has_offset", false);
    }

     */


//   private void initState() {
        /*
        // Sticks container to right or left
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mSlidingLayer.getLayoutParams();
        int textResource;
        Drawable d;


        textResource = R.string.swipe_left_label;
        d = getResources().getDrawable(R.drawable.container_rocket_left);

        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            textResource = R.string.swipe_label;
//            d = getResources().getDrawable(R.drawable.container_rocket);
//
//            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
//            rlp.width = LayoutParams.MATCH_PARENT;


        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        mSlidingLayer.setLayoutParams(rlp);

        // Sets the shadow of the container
        mSlidingLayer.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        mSlidingLayer.setOffsetWidth(getResources().getDimensionPixelOffset(R.dimen.offset_width));   */
//    }


    /*
    public void buttonClicked(View v) {
        switch (v.getId()) {
        case R.id.buttonOpen:
            if (!mSlidingLayer.isOpened()) {
                mSlidingLayer.openLayer(true);
            }
            break;
        case R.id.buttonClose:
            if (mSlidingLayer.isOpened()) {
                mSlidingLayer.closeLayer(true);
            }
            break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
        case KeyEvent.KEYCODE_BACK:
            if (mSlidingLayer.isOpened()) {
                mSlidingLayer.closeLayer(true);
                return true;
            }

        default:
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    */

    class CompanyAdapter extends ArrayAdapter<Company> {


        public CompanyAdapter(ArrayList<Company> array) {
            super(MainActivity.this, 0, array);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater()
                        .inflate(R.layout.company_item, parent, false);
            }

            Company c = getItem(position);

            ((TextView) convertView.findViewById(R.id.compny_title)).setText(c.getName());


            String descr = "Creator : " + c.getCreatorId()
                    + "\nProjects : 14" +
                    "\n" + c.getDescription();

            ((TextView) convertView.findViewById(R.id.company_description)).setText(descr);

            return convertView;
        }

        @Override
        public int getCount() {
            return mCompanyArrayList.size();
        }


    }

    void showToast(String text, int background) {
        SuperToast toast = new SuperToast(MainActivity.this);

        toast.setText(text);
        toast.setAnimations(SuperToast.Animations.FADE);

        toast.setDuration(SuperToast.Duration.LONG);
        toast.setBackground(background);
        toast.setTextSize(SuperToast.TextSize.MEDIUM);
        toast.setIcon(SuperToast.Icon.Light.INFO, SuperToast.IconPosition.LEFT);

        toast.show();
    }
}
