package com.example.vince.eatwise;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vince.eatwise.Utility.User;

import java.util.Date;

/*
 * Alternative to RecommendationList.java
 */

public class RecommendFrag extends Fragment {
    private View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_recommend, container, false);

        getActivity().setTitle("Recommendations");

        final TextView header = myView.findViewById(R.id.op_header);
        final TextView intro = myView.findViewById(R.id.rec_intro);
        final ListView list = myView.findViewById(R.id.rec_list);

        //TODO:get user after login
        //producing mock recommendation
        User user = new User("username", "12345", "xxxx@gmail.com", "Larry", "David", new Date());
        user.show_recommendation();
        String[] rec_name_list = new String[]{
                user.recList.data_member.get(0).name,
                user.recList.data_member.get(1).name,
                user.recList.data_member.get(2).name,
                user.recList.data_member.get(3).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name,
                user.recList.data_member.get(4).name
        };

        //adaptor
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.recommend_layout, rec_name_list);

        // Assign adapter to ListView
        list.setAdapter(adapter);

        // ListView Item Click Listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) list.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getActivity().getApplicationContext(),
                        "Position :"+itemPosition+"  Restaurant : " +itemValue, Toast.LENGTH_LONG)
                        .show();
            }
        });

        return myView;
    }
}