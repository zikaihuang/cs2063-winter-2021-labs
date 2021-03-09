package mobiledev.unb.ca.recyclerviewlab;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // TODO 1
        //  Get the intent that started this activity, and get the extras from it
        //  corresponding to the title and description of the course
        Intent intent = getIntent();
        String ctitle = intent.getStringExtra("Title");
        String description = intent.getStringExtra("Description");


       // TODO 2
       //  Set the description TextView to be the course description
        TextView detailNext = (TextView)findViewById(R.id.description_textview);
        detailNext.setText(description);

       // TODO 3
       //  Make the TextView scrollable
        detailNext.setMovementMethod(new ScrollingMovementMethod());

       // TODO 4
       //  Set the title of the action bar to be the course title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(detailNext);
        actionBar.setTitle(ctitle);
    }
}
