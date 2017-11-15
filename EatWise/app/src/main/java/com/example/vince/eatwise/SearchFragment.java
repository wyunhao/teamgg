package com.example.vince.eatwise;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vince.eatwise.QueryData.CuisineType;
import com.example.vince.eatwise.QueryData.QueryFilter;

public class SearchFragment extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_search, container, false);

        getActivity().setTitle(R.string.title_search_fragment);

        final Button submitFilter = myView.findViewById(R.id.buttonSubmit);
        final EditText location = myView.findViewById(R.id.editTextLocation);
        final EditText distance = myView.findViewById(R.id.editTextDistance);
        final Spinner category = myView.findViewById(R.id.spinnerCategory);
        category.setAdapter(new ArrayAdapter<CuisineType>(getActivity(), android.R.layout.simple_spinner_item, CuisineType.values()));
        final EditText price = myView.findViewById(R.id.editTextPrice);
        final EditText keyword = myView.findViewById(R.id.editTextKeyword);

        submitFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final QueryFilter filter = QueryFilter.builder().location(location.getText().toString())
                        .category((CuisineType)category.getSelectedItem())
                        .distance(Integer.parseInt(distance.getText().toString()))
                        .price(Double.parseDouble(price.getText().toString()))
                        .keyword(keyword.getText().toString().split(" "))
                        .build();

                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("filter", filter);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        return myView;
    }
}
