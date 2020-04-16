package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.ArrayList;
import java.util.List;

public class BodyPartFragment extends Fragment {

    // Tag for logging
    private static final String TAG = BodyPartFragment.class.getSimpleName();

    // keys to store state information about the list of images and list index,
    // for example when device is rotated
    static final String IMAGE_ID_LIST = "image_ids";
    static final String LIST_INDEX = "list_index";

    private List<Integer> imageIds;
    private int listItemIndex;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public BodyPartFragment() {}

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            imageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            listItemIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        // Get a reference to the ImageView in the fragment layout
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        // If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found
        if (imageIds != null) {
            // Set the image resource to the list item at the stored index
            imageView.setImageResource(imageIds.get(listItemIndex));

            // Set a click listener on the image view
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Increment position as long as the index remains <= the size of the image ids list
                    if (listItemIndex < imageIds.size() - 1) {
                        listItemIndex++;
                    } else {
                        // The end of list has been reached, so return to beginning index
                        listItemIndex = 0;
                    }
                    // Set the image resource to the new list item
                    imageView.setImageResource(imageIds.get(listItemIndex));
                }
            });

        } else {
            Log.v(TAG, "This fragment has a null list of image ids.");
        }

        // Return the rootView
        return rootView;
    }

    // Setter methods for keeping track of the list images this fragment can display and which image
    // in the list is currently being displayed
    public void setImageIds(List<Integer> imageIds)
    {
        this.imageIds = imageIds;
    }

    public void setListItemIndex(int listItemIndex)
    {
        this.listItemIndex = listItemIndex;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(IMAGE_ID_LIST, new ArrayList<>(imageIds));
        outState.putInt(LIST_INDEX, listItemIndex);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }
}
