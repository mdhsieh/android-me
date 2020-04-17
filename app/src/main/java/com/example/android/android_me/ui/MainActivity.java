package com.example.android.android_me.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This activity is responsible for displaying the master list of all images
public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    // keys to store body part indexes in bundle
    private static final String HEAD_INDEX = "headIndex";
    private static final String BODY_INDEX = "bodyIndex";
    private static final String LEG_INDEX = "legIndex";

    // there are 12 images for each body part
    private final static int NUM_BODY_PARTS = 12;

    // list index of each selected body part, ranging from 0 to 11
    // default value is 0
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    // next button
    Button nextButton;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = (Button) findViewById(R.id.next_button);

        // Determine if you're creating a two-pane or single-pane display
        if (findViewById(R.id.android_me_linear_layout) != null)
        {
            // This LinearLayout will only initially exist in the two-pane tablet case
            isTwoPane = true;

            // Change the GridView to 2 columns to space out the images more on tablet
            GridView gridView = (GridView) findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            // get rid of the next button that appears on phones for launching a separate activity
            nextButton.setVisibility(View.GONE);

            // In two-pane mode, add initial BodyPartFragments to the screen
            if (savedInstanceState == null) {

                // Creating a new head fragment
                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImageIds(AndroidImageAssets.getHeads());
                headFragment.setListItemIndex(headIndex);

                // New body fragment
                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());
                bodyFragment.setListItemIndex(bodyIndex);

                // New leg fragment
                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(AndroidImageAssets.getLegs());
                legFragment.setListItemIndex(legIndex);

                // Add the fragment to its container using a transaction
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        }
    }

    // Define the behavior for onImageSelected
    @Override
    public void onImageSelected(int position) {

        // Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments

        // there are 36 total images, ranging from positions 0 to 35

        // bodyPartNumber will be = 0 for the head fragment, 1 for the body, and 2 for the leg fragment
        // Dividing by 12 gives us these integer values because each list of images resources has a size of 12
        int bodyPartNumber = position / NUM_BODY_PARTS;

        // Store the correct list index no matter where in the image list has been clicked
        // This ensures that the index will always be a value between 0 - 11
        int listIndex = position - NUM_BODY_PARTS * bodyPartNumber;

        // Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        if (isTwoPane)
        {
            // Create two-pane interaction
            BodyPartFragment newFragment = new BodyPartFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            // Set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber)
            {
                case 0:
                    // A head image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setImageIds(AndroidImageAssets.getHeads());
                    newFragment.setListItemIndex(listIndex);

                    // Replace the old head fragment with a new one
                    fragmentManager.beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    newFragment.setImageIds(AndroidImageAssets.getBodies());
                    newFragment.setListItemIndex(listIndex);

                    fragmentManager.beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    newFragment.setImageIds(AndroidImageAssets.getLegs());
                    newFragment.setListItemIndex(listIndex);

                    fragmentManager.beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        }
        else
        {
            // Handle the single-pane phone case by passing information in a Bundle attached to an Intent

            // Set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }

            // Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
            Bundle bundle = new Bundle();
            bundle.putInt(HEAD_INDEX, headIndex);
            bundle.putInt(BODY_INDEX, bodyIndex);
            bundle.putInt(LEG_INDEX, legIndex);

            // Attach the Bundle to an intent
            final Intent launchAndroidMe = new Intent(this, AndroidMeActivity.class);
            launchAndroidMe.putExtras(bundle);

            // The "Next" button launches a new AndroidMeActivity
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(launchAndroidMe);
                }
            });
        }
    }
}
