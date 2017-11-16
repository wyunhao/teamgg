package com.example.vince.eatwise;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonArray;

import java.util.ArrayList;

import static android.view.Gravity.CENTER;

/**
 * This fragment displays the list of restaurants with names and images.
 */

public class ResultListFragment extends Fragment {
    private View myView;
    private JsonArray results;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_result_list, container, false);
        getActivity().setTitle(R.string.title_result_list);
        results = ((SearchResultActivity)getActivity()).getResults();

        populateResults();

        return myView;
    }

    private void populateResults() {
        String[] itemName = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            itemName[i]= results.get(i).getAsJsonObject().get("name").getAsString();
        }
        Integer imageID[] = new Integer[20];
        CustomListAdapter adapter=new CustomListAdapter(getActivity(), itemName, imageID);
        ListView list=(ListView)myView.findViewById(R.id.list);
        list.setAdapter(adapter);

        return;

    }



}

class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity contextActivity;
    private final String[] itemName;


    public CustomListAdapter(Activity contextActivity, String[] itemName, Integer[] imageID) {
        super(contextActivity, R.layout.list_row, itemName);
        // TODO Auto-generated constructor stub

        this.contextActivity=contextActivity;
        this.itemName = itemName;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=contextActivity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_row, null,true);

        TextView itemTitle = (TextView) rowView.findViewById(R.id.item);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        itemTitle.setText(itemName[position]);

        return rowView;

    };
}
