package com.jfpdazey.pathoflowestcost;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/*
Name: PathOfLowestActivityTest
Purpose: Test to see the grid and perform operations
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml", packageName = "com.jfpdazey.pathoflowestcost")
public class PathOfLowestActivityTest {
        private PathOfLowestActivity activity;

        //setup before test
    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(PathOfLowestActivity.class);
    }

    //test to get button disabled by default
    @Test
    public void goButtonIsDisabledByDefault() {
        Button goButton = (Button) activity.findViewById(R.id.go_button);
        assertThat(goButton.isEnabled(), is(false));
    }

    //test to check entering Any Text Into The Custom Grid Contents Enables The GoButton
    @Test
    public void enteringAnyTextIntoTheCustomGridContentsEnablesTheGoButton() {
        EditText customGridContents = (EditText) activity.findViewById(R.id.custom_grid_contents);
        Button goButton = (Button) activity.findViewById(R.id.go_button);

        customGridContents.setText("a");

        assertThat(goButton.isEnabled(), is(true));
    }

    //test to remove All Text From The Custom Grid Contents Disables The GoButton
    @Test
    public void removingAllTextFromTheCustomGridContentsDisablesTheGoButton() {
        EditText customGridContents = (EditText) activity.findViewById(R.id.custom_grid_contents);
        Button goButton = (Button) activity.findViewById(R.id.go_button);
        customGridContents.setText("a");

        customGridContents.setText("");

        assertThat(goButton.isEnabled(), is(false));
    }

    //test to clickingGoWithLessThanFiveColumnsOfDataDisplaysErrorMessage
    @Test
    public void clickingGoWithLessThanFiveColumnsOfDataDisplaysErrorMessage() {
        EditText customGridContents = (EditText) activity.findViewById(R.id.custom_grid_contents);

        customGridContents.setText("1 2 3 4");
        activity.findViewById(R.id.go_button).performClick();

        ShadowAlertDialog alertDialog = Shadows.shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        assertThat(alertDialog.getTitle().toString(), equalTo("Invalid Grid"));
        assertThat(alertDialog.getMessage().toString(), equalTo(activity.getResources().getString(R.string.invalid_grid_message)));
    }

    //test to clicking Go With More Than OneHundred Columns Of Data Displays Error Message
    @Test
    public void clickingGoWithMoreThanOneHundredColumnsOfDataDisplaysErrorMessage() {
        EditText customGridContents = (EditText) activity.findViewById(R.id.custom_grid_contents);
        StringBuilder inputBuilder = new StringBuilder();
        for (int i = 1; i <= 101; i++) {
            inputBuilder.append(i).append(" ");
        }

        customGridContents.setText(inputBuilder.toString());
        activity.findViewById(R.id.go_button).performClick();

        ShadowAlertDialog alertDialog = Shadows.shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        assertThat(alertDialog.getTitle().toString(), equalTo("Invalid Grid"));
        assertThat(alertDialog.getMessage().toString(), equalTo(activity.getResources().getString(R.string.invalid_grid_message)));
    }

    //test to clickingGoWithNonNumericDataDisplaysErrorMessage
    @Test
    public void clickingGoWithNonNumericDataDisplaysErrorMessage() {
        EditText customGridContents = (EditText) activity.findViewById(R.id.custom_grid_contents);

        customGridContents.setText("1 2 3 4 b");
        activity.findViewById(R.id.go_button).performClick();

        ShadowAlertDialog alertDialog = Shadows.shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        assertThat(alertDialog.getTitle().toString(), equalTo("Invalid Grid"));
        assertThat(alertDialog.getMessage().toString(), equalTo(activity.getResources().getString(R.string.invalid_grid_message)));
    }

    //test to clickingGoWithValidDataDisplaysYesIfPathSuccessful
    @Test
    public void clickingGoWithValidDataDisplaysYesIfPathSuccessful() {
        EditText customGridContents = (EditText) activity.findViewById(R.id.custom_grid_contents);

        customGridContents.setText("1 2 3 4 5");
        activity.findViewById(R.id.go_button).performClick();

        TextView resultsView = (TextView) activity.findViewById(R.id.results_success);
        assertThat(resultsView.getText().toString(), equalTo("Yes"));
    }

    //test to clickingGoAfterClickingAGridButtonDisplaysNoIfPathNotSuccessful
    @Test
    public void clickingGoAfterClickingAGridButtonDisplaysNoIfPathNotSuccessful() {
        EditText customGridContents = (EditText) activity.findViewById(R.id.custom_grid_contents);

        customGridContents.setText("50 2 3 4 5");
        activity.findViewById(R.id.go_button).performClick();

        TextView resultsView = (TextView) activity.findViewById(R.id.results_success);
        assertThat(resultsView.getText().toString(), equalTo("No"));
    }

    //test to clickingGoAfterClickingAGridButtonDisplaysTotalCostOfPathOnSecondLineOfResults
    @Test
    public void clickingGoAfterClickingAGridButtonDisplaysTotalCostOfPathOnSecondLineOfResults() {
        EditText customGridContents = (EditText) activity.findViewById(R.id.custom_grid_contents);

        customGridContents.setText("1 2 3 4 5");
        activity.findViewById(R.id.go_button).performClick();

        TextView resultsView = (TextView) activity.findViewById(R.id.results_total_cost);
        assertThat(resultsView.getText().toString(), equalTo("15"));
    }

    //test to clickingGoAfterClickingAGridButtonDisplaysPathTakenOnThirdLineOfResults
    @Test
    public void clickingGoAfterClickingAGridButtonDisplaysPathTakenOnThirdLineOfResults() {
        EditText customGridContents = (EditText) activity.findViewById(R.id.custom_grid_contents);

        customGridContents.setText("1 2 3 4 5 6\n2 1 2 2 2 2\n3 3 1 3 3 3\n4 4 4 1 1 4\n5 5 5 5 5 1\n6 6 6 6 6 6");
        activity.findViewById(R.id.go_button).performClick();

        TextView resultsView = (TextView) activity.findViewById(R.id.results_path_taken);
        assertThat(resultsView.getText().toString(), equalTo("1\t2\t3\t4\t4\t5"));
    }

}