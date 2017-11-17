package com.example.vince.eatwise;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import com.example.vince.eatwise.QueryData.CuisineType;
import com.example.vince.eatwise.QueryData.QueryFilter;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private View myView;

    /**
     * generate a search user interface for a user to customize their filter objects
     * which be used to build up the query
     * extract the user inputs and combine them into a parameter bundle
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, final Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_search, container, false);
        getActivity().setTitle(R.string.title_search_fragment);

        setSoftKeyboardHiddenListener(myView);

        final Button submitFilter = myView.findViewById(R.id.buttonSubmit);
        final EditText location = myView.findViewById(R.id.editTextLocation);
        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (location.getText().length() == 0) {
                    location.setError("Please specify the search address.");
                }
            }
        });//set error for location if user does not specify one, real-time validation
        final EditText distance = myView.findViewById(R.id.editTextDistance);
        distance.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                try {
                    Integer.parseInt(distance.getText().toString());
                } catch (NumberFormatException e) {
                    distance.setError("Please enter a valid range of distance.");
                }
            }
        });//set error for distance if user does not specify one, real-time validation

        List<String> categorySpinner = new ArrayList();
        for (CuisineType type : CuisineType.values()) {
            categorySpinner.add(type.toString());
        }
        categorySpinner.add(0, "Select food category");
        ArrayAdapter<String> categoryString= new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categorySpinner);
        categoryString.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner category = myView.findViewById(R.id.spinnerCategory);
        category.setAdapter(categoryString);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos == 0) {
                    final TextView selected = (TextView) adapterView.getSelectedView();
                    selected.setTextColor(Color.GRAY);
                    selected.setError("Please select a food category");
                }
            }//set error for category spinner if the selected item is default string msg

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (adapterView.getSelectedItemId() == 0) {
                    ((TextView) adapterView.getSelectedView()).setError("Please select a food category");
                }
            }
        });

        final EditText price = myView.findViewById(R.id.editTextPrice);
        price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                try {
                    Double.parseDouble(price.getText().toString());
                } catch (NumberFormatException e) {
                    price.setError("Please enter a valid price level.");
                }
            }
        });//set error for price level if user does not specify one, real-time validation
        final EditText keyword = myView.findViewById(R.id.editTextKeyword);

        submitFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String kw = "";
                try {
                    kw = keyword.getText().toString();
                } catch (Exception e) {

                }
                if (location.getError() != null || distance.getError() != null || price.getError() != null
                        || ((TextView) category.getSelectedView()).getError() != null) {
                    Toast.makeText(getActivity(), "Please complete the search filter", Toast.LENGTH_SHORT).show();
                }//if there is still error in any edittext boxes, show the Toast to let user complete the search filter
                else {
                    final QueryFilter filter = QueryFilter.builder().location(location.getText().toString())
                            .category((String) category.getSelectedItem())
                            .distance(Integer.parseInt(distance.getText().toString()))
                            .price(Double.parseDouble(price.getText().toString()))
                            .keyword(kw)
                            .build();

                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("filter", filter);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

        return myView;
    }


    private void setSoftKeyboardHiddenListener(View myView) {
        myView.findViewById(R.id.relataivesearchlayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                        .getSystemService(getActivity().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return true;
            }
        });
    }
}
