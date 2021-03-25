package mobiledev.unb.ca.roompersistencelab;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;

import java.util.List;
import java.util.ArrayList;

import mobiledev.unb.ca.roompersistencelab.ui.ItemViewModel;
import mobiledev.unb.ca.roompersistencelab.ui.ItemsAdapter;
import mobiledev.unb.ca.roompersistencelab.entity.Item;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ItemViewModel mItemViewModel;
    private ItemsAdapter mItemsAdapter;
    private ListView mListView;

    private Button mAddButton;
    private EditText mSearchEditText;
    private EditText mItemEditText;
    private EditText mNumberEditText;
    private TextView mResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the references for the views defined in the layout files
        mAddButton = findViewById(R.id.add_button);
        mSearchEditText = findViewById(R.id.search_edit_text);
        mItemEditText = findViewById(R.id.item_edit_text);
        mNumberEditText = findViewById(R.id.number_edit_text);
        mResultsTextView = findViewById(R.id.results_text_view);
        mListView = findViewById(R.id.listview);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mItemEditText.getText().toString().matches("")||!(mNumberEditText.getText().toString().matches(""))){
                    addItem(mItemEditText.getText().toString(), mNumberEditText.getText().toString());
                }
                else{
                        Toast.makeText(getApplicationContext(),"The data entered was incomplete.", Toast.LENGTH_SHORT).show();
                }
            }
                // TODO Check if some text has been entered in both the item and number
                //  EditTexts.
                //  If so call the addItem method using the the text from these EditTexts.
                //  If not display a toast indicating that the data entered was incomplete.
        });

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO v is the search EditText. (EditText is a subclass of TextView.)
                    //  Get the text from this view.
                    //  Call the searchRecords method using the item name.
                    searchRecords(v.getText().toString());
                }

                return false;
            }
        });

        // Set the ViewModel
        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
    }

    private void addItem(String item, String num) {
        // TODO Make a call to the view model to create a record in the database table
        mItemViewModel.insert(item, Integer.parseInt(num));
        // TODO You will need to write a bit of extra code to get the
        //  UI to behave nicely, e.g., showing and hiding the keyboard
        //  at the right time, clearing text fields appropriately. Some
        //  of that code will likely go here, but you might also make
        //  changes elsewhere in the app. Exactly how you make the
        //  UI behave is up to you, but you should make reasonable
        //  choices.
        mItemEditText.setText("");
        mNumberEditText.setText("");
        hideKeyboard(this);
    }

    private void searchRecords(String item) {
        // TODO Make a call to the view model to search for records in the database that match the query item.
        //  Make sure that the results are sorted appropriately
        List<Item> items;
        items = mItemViewModel.search(item);

        // TODO Update the results section.
        //  If there are no results, set the results TextView to indicate that there are no results.
        //  If there are results, set the results TextView to indicate that there are results.
        //  Again, you might need to write a bit of extra code here or elsewhere, to get the UI to behave nicely.
        if (items.isEmpty()) {
            mResultsTextView.setText("There are no result.");
        }
        else {
            mResultsTextView.setText(items.size() + " result(s) found.");
            ArrayList<String> allResults = new ArrayList<>();
            for (int i=0; i<items.size(); i++) {
                allResults.add(items.get(i).getName() + " " + items.get(i).getNum());
            }

            ArrayAdapter itemAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allResults);
            mListView.setAdapter(itemAdapter);
        }
        hideKeyboard(this);
        mSearchEditText.setText("");
    }
    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //if(activity.getCurrentFocus() != null){
        //    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        //}
}
