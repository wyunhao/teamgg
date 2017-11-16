package com.example.vince.eatwise;

/*
* Created by Zhichun Li on 2017-11-16
* */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vince.eatwise.Utility.User;

import java.util.Date;
/*
 *  Last update by Zhichun Li on 2017-11-15
 *  */

public class RecommendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        final TextView header = findViewById(R.id.op_header);
        final TextView intro = findViewById(R.id.rec_intro);
        final ListView list = findViewById(R.id.rec_list);

        //TODO:get user after login
        //producing mock recommendation
        User user = new User("username", "12345", "xxxx@gmail.com", "Larry", "David", new Date());
        user.show_recommendation();
        String[] rec_name_list = new String[]{
                user.recList.data_member.get(0).name,
                user.recList.data_member.get(1).name,
                user.recList.data_member.get(2).name,
                user.recList.data_member.get(3).name,
                user.recList.data_member.get(4).name
        };

        //adaptor
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, rec_name_list);

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
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}

