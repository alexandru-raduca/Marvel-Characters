package alexandruraduca.example.marvelcharacters.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import alexandruraduca.example.marvelcharacters.R;
import alexandruraduca.example.marvelcharacters.adapters.MarvelRecyclerAdapter;
import alexandruraduca.example.marvelcharacters.models.MarvelCharacter;
import alexandruraduca.example.marvelcharacters.viewmodels.MainActivityViewModel;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final static Integer INITIAL_OFFSET = 0;

    private SearchView searchView;
    private ConstraintLayout mConstraintLayout;
    private TextView mAppTitle;


    private RecyclerView mRecyclerView;
    private MarvelRecyclerAdapter mAdapter;
    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConstraintLayout = findViewById(R.id.layout_bg);
        mAppTitle = findViewById(R.id.titleView);

        mRecyclerView = findViewById(R.id.recycler_view);

        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        searchView = findViewById(R.id.search_character_bar);

        initRecyclerView();
        initSearchView();
        subscribeObservers();

        //set OnTouchListener for the soft keyboard when tapping outside of the search bar or on any other component
        hideSoftKeyboard();
    }

    public void searchQuery(View view){
        searchView.setQuery(searchView.getQuery(), true);
    }

    private void subscribeObservers() {
        mMainActivityViewModel.getCharacters().observe(this, new Observer<List<MarvelCharacter>>() {

            @Override
            public void onChanged(List<MarvelCharacter> marvelCharacters) {
                if (marvelCharacters != null) {
                    mAdapter.setCharacters(marvelCharacters);
                    mMainActivityViewModel.setRetrievedMarvel(true);
                }
            }
        });

        mMainActivityViewModel.isMarvelRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean && !mMainActivityViewModel.didRetrieveMarvel()){
                    Toast.makeText(MainActivity.this, "Sorry! I can't update the feed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new MarvelRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mMainActivityViewModel.getMarvelApi(INITIAL_OFFSET);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!mRecyclerView.canScrollVertically(1)) {
                    mMainActivityViewModel.getNextResults();
                }
            }
        });
    }

    private void initSearchView() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMainActivityViewModel.searchMarvelApi(query);

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() < 1) {
                    mMainActivityViewModel.getMarvelApi(INITIAL_OFFSET);
                } else {

                    mMainActivityViewModel.searchMarvelApiByInitials(newText);
                }
                return false;
            }

        });
    }

    private void hideSoftKeyboard() {
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                searchView.clearFocus();
                return false;
            }
        });

        mConstraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                searchView.clearFocus();
                return true;
            }
        });

        mAppTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                searchView.clearFocus();
                return true;
            }
        });
    }
}
