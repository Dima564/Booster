package com.slidinglayersample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author bamboo
 * @since 3/22/14 6:34 PM
 */
public class MainFragment extends Fragment {

    ListView mListViewTask;
    ArrayList<Task> mTaskArrayList = new ArrayList<Task>();
    TaskAdapter mTaskAdapter;

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm  MMM dd, yyyy");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_diagram_layout, container, false);

        mListViewTask = (ListView) v.findViewById(R.id.task_list_view);
        initState();



        return v;
    }

    void initState() {

        for (int i = 0; i < 30; i++) {
            mTaskArrayList.add(new Task());
        }


        mTaskAdapter = new TaskAdapter(mTaskArrayList);
        mListViewTask.setAdapter(mTaskAdapter);

    }

    ;

    class TaskAdapter extends ArrayAdapter<Task> {


        public TaskAdapter(ArrayList<Task> array) {
            super(MainFragment.this.getActivity(), 0, array);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.task_simple_item, parent, false);
            }

            Task c = getItem(position);

            ((TextView) convertView.findViewById(R.id.task_title)).setText(c.getName());


            String descr = "Start date : " + dateFormat.format(c.getStartDate())
                    + "\nDue date : " + dateFormat.format(c.getDueDate())
                    + "\nPoints : " + c.getMaxValue()
                    + "\n" + c.getDescription();


            ((TextView) convertView.findViewById(R.id.task_description)).setText(descr);

            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.task_checkbox_done);
            checkBox.setChecked(c.isDone());
            checkBox.setText(c.isConfirmed() ? "  Confirmed" : "  Not confirmed");


            return convertView;
        }

        @Override
        public int getCount() {
            return mTaskArrayList.size();
        }
    }

}