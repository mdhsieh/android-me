package com.example.android.android_me.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_me.R;

// This activity is responsible for displaying the master list of all images
public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    // keys to store body part indexes in bundle
    private static final String HEAD_INDEX = "head-index";
    private static final String BODY_INDEX = "body-index";
    private static final String LEG_INDEX = "leg-index";

    // there are 12 images for each body part
    private final static int NUM_BODY_PARTS = 12;

    // list index of each selected body part, ranging from 0 to 11
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    // next button
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = (Button) findViewById(R.id.next_button);
    }

    // Define the behavior for onImageSelected
    @Override
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked: " + position, Toast.LENGTH_SHORT)
                .show();

        // Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments

        // there are 36 total images, ranging from positions 0 to 35

        // bodyPartNumber will be = 0 for the head fragment, 1 for the body, and 2 for the leg fragment
        // Dividing by 12 gives us these integer values because each list of images resources has a size of 12
        int bodyPartNumber = position / NUM_BODY_PARTS;

        // Store the correct list index no matter where in the image list has been clicked
        // This ensures that the index will always be a value between 0-11
        int listIndex = position - NUM_BODY_PARTS * bodyPartNumber;

        // Set the currently displayed item for the correct body part fragment
        switch (bodyPartNumber)
        {
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
                // Verify that the intent will resolve to an activity
                /*
                    The if statement is not needed here but is for future reference.
                    This will be null, for example, if a user doesn't have any apps that
                    can handle the intent, or the app to handle the intent is inaccessible.
                 */
                if (launchAndroidMe.resolveActivity(getPackageManager()) != null) {
                    startActivity(launchAndroidMe);
                }
                else
                {
                    Log.v(TAG, "No Intent available to handle action.");
                }
            }
        });
    }
}
