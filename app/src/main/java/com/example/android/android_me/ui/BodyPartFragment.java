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

import java.util.List;

public class BodyPartFragment extends Fragment {

    // Tag for logging
    private final static String TAG = BodyPartFragment.class.getSimpleName();

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

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        // Get a reference to the ImageView in the fragment layout
        ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        // If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found
        if (imageIds != null)
        {
            // Set the image resource to the list item at the stored index
            imageView.setImageResource(imageIds.get(listItemIndex));
        }
        else
        {
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
}
