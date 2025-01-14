package mobiledev.unb.ca.recyclerviewlab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mobiledev.unb.ca.recyclerviewlab.model.Course;
import mobiledev.unb.ca.recyclerviewlab.util.JsonUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    //private Context context;
    //private ArrayList<Course> courses;
    //ArrayList<Course> courses = new ArrayList<Course>();
    private MyAdapter mAdapter;
    //private Object description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO 1
        //  Get the ArrayList of Courses from the JsonUtils class
        //  (Ideally we would do this loading off of the main thread. We'll get to that
        //  in the next lab. Today we're focusing on displaying scrolling lists.
        ArrayList<Course> courses = new ArrayList<Course>();
        JsonUtils json = new JsonUtils(this);
        courses = json.getCourses();

        // TODO 2
        //  Get a reference to the RecyclerView and set its adapter
        //  to be an instance of MyAdapter, which you will need to create
        //  using the ArrayList of courses from above.
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new MyAdapter(courses);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    // The RecyclerView.Adapter class provides a layer of abstraction between the
    // RecyclerView's LayoutManager and the underlying data that is being displayed,
    // in this case the ArrayList of Course objects.
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<Course> mDataset;

        public MyAdapter(ArrayList<Course> myDataset) {
            mDataset = myDataset;
        }

        // ViewHolder represents an individual item to display. In this case
        // it will just be a single TextView (displaying the title of a course)
        // but RecyclerView gives us the flexibility to do more complex things
        // (e.g., display an image and some text).
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(TextView v) {
                super(v);
                mTextView = v;
            }
        }

        // The inflate method of the LayoutInflater class can be used to obtain the
        // View object corresponding to an XML layout resource file. Here
        // onCreateViewHolder inflates the TextView corresponding to item_layout.xml
        // and uses it to instantiate a ViewHolder.
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);

            return new ViewHolder(v);
        }


        // onBindViewHolder binds a ViewHolder to the data at the specified
        // position in mDataset
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // TODO 3
            //  Get the Course at index position in mDataSet
            //  (Hint: you might need to declare this variable as final.)
            final Course course;
            course = mDataset.get(position);


            // TODO 4
            //  Set the TextView in the ViewHolder (holder) to be the title for this Course
            //public void set_title(TextView text)
            //{
            //    ViewHolder view = new ViewHolder(text);
            //}
            holder.mTextView.setText((CharSequence) course.getTitle());



          // TODO 5
          //  Set the onClickListener for the TextView in the ViewHolder (holder) such
          //  that when it is clicked, it creates an explicit intent to launch DetailActivity
          //  HINT: You will need to put two extra pieces of information in this intent:
          //      The Course title and it's description'
            holder.mTextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,DetailActivity.class)
                            .putExtra("title",course.getTitle())
                            .putExtra("description", course.getDescription());
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
