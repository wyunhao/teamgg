package com.example.vince.eatwise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vince.eatwise.QueryData.CuisineType;
import com.example.vince.eatwise.QueryData.QueryFilter;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Button submitFilter = findViewById(R.id.buttonSubmit);
        final EditText location = findViewById(R.id.editTextLocation);
        final EditText distance = findViewById(R.id.editTextDistance);
        final Spinner category = findViewById(R.id.spinnerCategory);
        category.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CuisineType.values()));
        final EditText price = findViewById(R.id.editTextPrice);
        final EditText keyword = findViewById(R.id.editTextKeyword);

        submitFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final QueryFilter filter = QueryFilter.builder().location(location.getText().toString())
                        .category((CuisineType)category.getSelectedItem())
                        .distance(Integer.parseInt(distance.getText().toString()))
                        .price(Double.parseDouble(price.getText().toString()))
                        .keyword(keyword.getText().toString().split(" "))
                        .build();

                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("filter", filter);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }


    //private boolean checkValidInput(){}
}
