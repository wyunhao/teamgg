package com.example.vince.eatwise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vince.eatwise.QueryData.CruisineType;
import com.example.vince.eatwise.QueryData.QueryFilter;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Button submitFilter = findViewById(R.id.button_id);
        final EditText location = findViewById(R.id.editTextLocation);
        final EditText distance = findViewById(R.id.editTextDistance);
        final Spinner category = findViewById(R.id.spinnerCategory);
        category.setAdapter(new ArrayAdapter<CruisineType>(this, android.R.layout.simple_spinner_item, CruisineType.values()));
        final EditText price = findViewById(R.id.editTextPrice);
        final EditText keyword = findViewById(R.id.editTextKeyword);
/*
        final QueryFilter filter = QueryFilter.builder().location(location.getText().toString())
                .category(category.getDropDownHorizontalOffset())
                .distance(distance.getText())
                .price(price.getText())
                .keyword(keyword.getText())
                .build();
*/
        submitFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                //Bundle b = new Bundle();
                //b.putSerializable("filter", filter);
                //intent.putExtras(b);
                startActivity(intent);
            }
        });
    }


    //private boolean checkValidInput(){}
}
