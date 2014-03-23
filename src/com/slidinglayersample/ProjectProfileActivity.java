package com.slidinglayersample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.*;
import android.widget.*;
import com.github.johnpersano.supertoasts.SuperToast;

import java.util.ArrayList;

/**
 * @author bamboo
 * @since 3/23/14 12:50 AM
 */
public class ProjectProfileActivity extends Activity {

    public static final String KEY_PROJECT = "oyster.project";

    private Drawable mActionBarBackgroundDrawable;

    private boolean mIsOverScrollEnabled = true;

    private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
        }
    };


    private Project mProject;


    ListView mListViewTasks;
    ArrayList<Task> mTaskArrayList = new ArrayList<Task>();
    TaskAdapter mTaskAdapter;

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_profile_activity);


        mActionBarBackgroundDrawable = getResources().getDrawable(R.drawable.gradient_action_bar/*R.drawable.ab_background*/);
        mActionBarBackgroundDrawable.setAlpha(0);

        getActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);

        ((NotifyingScrollView) findViewById(R.id.scroll_view)).setOnScrollChangedListener(mOnScrollChangedListener);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mActionBarBackgroundDrawable.setCallback(mDrawableCallback);
        }


        Bundle bundle = getIntent().getExtras();
        mProject = (Project) bundle.getSerializable(KEY_PROJECT);

        setTitle(mProject.getName());


//        mListViewTasks = (ListView) findViewById(R.id.task_list_view);
//        initState();

    }

    private void initState() {

        for (int i = 0; i < 10; i++) {
            Task c = new Task();
            // c.setName("Company Name");
            mTaskArrayList.add(c);
        }

        mTaskAdapter = new TaskAdapter(mTaskArrayList);
        mListViewTasks.setAdapter(mTaskAdapter);

        mListViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Intent i = new Intent(ProjectProfileActivity.this, TaskProfileActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(ProjectProfileActivity.KEY_PROJECT, mTaskAdapter.getItem(position));
//                i.putExtras(bundle);
//
//
//                startActivity(i);
            }
        });
    }

    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            final int headerHeight = findViewById(R.id.image_header).getHeight() - getActionBar().getHeight();
            final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
            final int newAlpha = (int) (ratio * 255);
            mActionBarBackgroundDrawable.setAlpha(newAlpha);
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_sign_out:
                User.signOutUser();
                ProjectProfileActivity.this.finish();
                startActivity(new Intent(ProjectProfileActivity.this, RegisterOrSignInActivity.class));
                return true;

            case R.id.action_new_task:

                AlertDialog.Builder builder = new AlertDialog.Builder(ProjectProfileActivity.this);

                LayoutInflater inflater = ProjectProfileActivity.this.getLayoutInflater();
                final View v = inflater.inflate(R.layout.dialog_create_task, null, false);
                builder.setView(v);

                builder.setTitle("Add task");


                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

/*
                        Company c = new Company();

                        EditText name = (EditText) v.findViewById(R.id.reg_company_name);
                        EditText desc = (EditText) v.findViewById(R.id.reg_company_description);


                        c.setName(name.getText().toString());
                        c.setDescription(desc.getText().toString());
                        c.setCreatorId(User.getCurrentUser().getId());

//                        mCompanyArrayList.add(c);
//                        mCompanyAdapter.notifyDataSetChanged();
                                   */
                        String s = ((EditText) v.findViewById(R.id.reg_task_name)).getText().toString();

                        showToast("Added task : " + s, SuperToast.Background.BLUE);
//                        Toast.makeText(getApplicationContext(), mSelectedItems.size(), Toast.LENGTH_LONG).show();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project_profile_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    void showToast(String text, int background) {
        SuperToast toast = new SuperToast(ProjectProfileActivity.this);

        toast.setText(text);
        toast.setAnimations(SuperToast.Animations.FADE);

        toast.setDuration(SuperToast.Duration.LONG);
        toast.setBackground(background);
        toast.setTextSize(SuperToast.TextSize.MEDIUM);
        toast.setIcon(SuperToast.Icon.Light.INFO, SuperToast.IconPosition.LEFT);

        toast.show();
    }


    class TaskAdapter extends ArrayAdapter<Task> {


        public TaskAdapter(ArrayList<Task> array) {
            super(ProjectProfileActivity.this, 0, array);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater()
                        .inflate(R.layout.task_simple_item, parent, false);
            }

            Task c = getItem(position);
              /*
            ((TextView) convertView.findViewById(R.id.compny_title)).setText(c.getName());


            String descr = "Creator : " + c.getCreatorId()
                    + "\nTasks : 14" +
                    "\n" + c.getDescription();

            ((TextView) convertView.findViewById(R.id.company_description)).setText(descr);
                             */
            return convertView;
        }

        @Override
        public int getCount() {
            return mTaskArrayList.size();
        }


    }


}