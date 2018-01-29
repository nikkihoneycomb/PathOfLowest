package com.jfpdazey.pathoflowestcost;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/*
Name: PathOfLowestActivity
Purpose: Activity defined to perform operations on grid
 */

public class PathOfLowestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowest_cost);


        //button,edittext initialization and its onclick listener methods
        Button goButton = (Button) findViewById(R.id.go_button);
        goButton.setOnClickListener(new GoOnClickListener());

        EditText customGridContents = (EditText) findViewById(R.id.custom_grid_contents);
        customGridContents.addTextChangedListener(new GridContentsWatcher());

    }

    /*
    method to get the string value and parse each element with respect to rows
     */
    private String formatPath(PathState path) {
        StringBuilder builder = new StringBuilder();
        List<Integer> rows = path.getRowsTraversed();

        for (int i = 0; i < rows.size(); i++) {
            builder.append(rows.get(i));
            if (i < rows.size() - 1) {
                builder.append("\t");
            }
        }

        return builder.toString();
    }

    /*
    method to check if the elements entered by user are valid
     */
    private boolean gridContentsAreValid(int[][] contents) {
        if (contents.length < 1 || contents.length > 10 || contents[0].length < 5 || contents[0].length > 100) {
            return false;
        } else {
            return true;
        }
    }

    /*
    method to load grid from string
     */
    private void loadGrid(int[][] contents) {
        Grid validGrid = new Grid(contents);
        GridVisitor visitor = new GridVisitor(validGrid);
        PathState bestPath = visitor.getBestPathForGrid();

        if (bestPath.isSuccessful()) {
            ((TextView) findViewById(R.id.results_success)).setText("Yes");
        } else {
            ((TextView) findViewById(R.id.results_success)).setText("No");
        }
        ((TextView) findViewById(R.id.results_total_cost)).setText(Integer.toString(bestPath.getTotalCost()));
        ((TextView) findViewById(R.id.results_path_taken)).setText(formatPath(bestPath));
        ((TextView) findViewById(R.id.grid_contents)).setText(validGrid.asDelimitedString("\t"));
    }

    /*
    method related to UI - to enable button only if atleast one element is entered into the editext area.
     */
    class GridContentsWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Button goButton = (Button) findViewById(R.id.go_button);
            goButton.setEnabled(!s.toString().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) { }
    }

    /*
    onclick listener for go button
     */
    class GoOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String gridString = ((EditText) findViewById(R.id.custom_grid_contents)).getText().toString();
            int[][] potentialGridContents = GridUtils.gridArrayFromString(gridString);
            if (gridContentsAreValid(potentialGridContents)) {
                loadGrid(potentialGridContents);
            } else {
                new AlertDialog.Builder(PathOfLowestActivity.this)
                        .setTitle("Invalid Grid")
                        .setMessage(R.string.invalid_grid_message)
                        .setPositiveButton("OK", null)
                        .show();
            }
        }
    }
}
