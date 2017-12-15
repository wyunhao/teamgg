package com.example.vince.eatwise.Utility;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vince.eatwise.R;

import lombok.AllArgsConstructor;

public class CustomListAdapter extends ArrayAdapter<String> implements AsyncResponse {

    private Activity contextActivity;
    private String[] itemName;
    private String[] itemRating;
    private String[] imageURL;

    public CustomListAdapter(final Activity contextActivity, final String[] itemName, final String[] itemRating,
                             final String[] imageURL) {
        super(contextActivity, R.layout.list_row, itemName);
        // TODO Auto-generated constructor stub

        this.contextActivity=contextActivity;
        this.itemName = itemName;
        this.itemRating = itemRating;
        this.imageURL = imageURL;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     * @param position int: The position of the item within the adapter's data set of the item whose view we want.
     * @param view View: The old view to reuse, if possible.
     * @param parent ViewGroup: The parent that this view will eventually be attached to. This value must never be null.
     * @return
     * @throws NumberFormatException
     */
    public View getView(final int position, final View view, final ViewGroup parent) throws NumberFormatException{
        final LayoutInflater inflater=contextActivity.getLayoutInflater();
        final View rowView=inflater.inflate(R.layout.list_row, null,true);

        TextView title = rowView.findViewById(R.id.item);
        TextView rating = rowView.findViewById(R.id.item_rating);
//        ImageView restaurant_image = rowView.findViewById(R.id.icon);

//        GetImage imageGetter = new GetImage(this);
//        Drawable d = null;
//        try {
//            d = imageGetter.execute(this.imageURL[position]).get();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        restaurant_image.setImageDrawable(d);

        title.setText(this.itemName[position]);
        rating.setText(this.itemRating[position] + "/5.0");
        double rate = Double.parseDouble(itemRating[position]);

        // red4-5, orange3-4, lightorange2-3, yellow1-2, grey0-1
        if (rate >= 4 && rate <= 5) {
            rating.setTextColor(Color.parseColor("#ff067c"));
        } else if (rate >= 3 && rate < 4) {
            rating.setTextColor(Color.parseColor("#e9743f"));
        } else if (rate >= 2 && rate < 3) {
            rating.setTextColor(Color.parseColor("#ff9e00"));
        } else if (rate >= 1 && rate < 2) {
            rating.setTextColor(Color.parseColor("#fede86"));
        } else {
            rating.setTextColor(Color.parseColor("#d8d8d0"));
        }

        return rowView;
    }

    public void processFinish(final String output){
        return;
    }

}
