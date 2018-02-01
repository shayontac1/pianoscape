package piano.pianotrainer.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import piano.pianotrainer.R;

public class SummaryActivity extends AppCompatActivity {
    private static final String SUMMARY_PREFS = "SummaryPrefs";
    SharedPreferences sharedpreferences;
    private int correctNotes = 0;
    private int totalNotes = 0;
    private double accuracyRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String filename = getIntent().getStringExtra("filename");

        // set shared preferences
        SharedPreferences.Editor editor = getSharedPreferences(SUMMARY_PREFS, MODE_PRIVATE).edit();
        editor.putString(filename, "65/70");
        editor.commit();

        // get shared preferences
        sharedpreferences = getSharedPreferences(SUMMARY_PREFS, MODE_PRIVATE);
        String summaryScore = sharedpreferences.getString(filename, null);
        String[] scoreArray = summaryScore.split("/");
        correctNotes = Integer.parseInt(scoreArray[0]);
        totalNotes = Integer.parseInt(scoreArray[1]);
        accuracyRate = ((double) correctNotes / (double) totalNotes) *100;

        TextView summaryTextView = (TextView) findViewById(R.id.summaryText);
        summaryTextView.setText("Notes correctly played: " + scoreArray[0] + "/" + scoreArray[1] + ". Accuracy: " + String.format("%.2f", accuracyRate) + "%");
    }

}
