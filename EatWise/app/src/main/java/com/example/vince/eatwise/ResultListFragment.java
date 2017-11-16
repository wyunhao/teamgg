package com.example.vince.eatwise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonArray;

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

        for (int i = 0; i < results.size(); i++) {
            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            TextView textView = new TextView(getContext());

            String name = results.get(i).getAsJsonObject().get("name").getAsString();
            textView.setText(name);

//            RelativeLayout.LayoutParams lpView = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 100);
//            lpView.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            lpView.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//
//            relativeLayout.setLayoutParams(lpView);
//            relativeLayout.addView(textView);
//            relativeLayout.setGravity(CENTER);




            LinearLayout resultForm = myView.findViewById(R.id.result_form);
            resultForm.addView(relativeLayout);
        }

        return;

    }



}
