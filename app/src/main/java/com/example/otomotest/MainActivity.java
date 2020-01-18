package com.example.otomotest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.SettingInjectorService;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.otomotest.adapter.OwnerAdapter;
import com.example.otomotest.model.Owner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private OwnerAdapter adapter;
    private RecyclerView rvMovie;
    private ArrayList<Owner> owners;

    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new OwnerAdapter();
        adapter.notifyDataSetChanged();


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);


        rvMovie = findViewById(R.id.rv_data);
        rvMovie.setHasFixedSize(true);


        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        rvMovie.setAdapter(adapter);

        ((SimpleItemAnimator) rvMovie.getItemAnimator()).setSupportsChangeAnimations(false);


        progressBar = findViewById(R.id.progressBar);


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

//        mainViewModel.setData();
        mainViewModel.setPost();
        mainViewModel.getDatas().observe(this, getDatas);
    }

    private Observer<ArrayList<Owner>> getDatas = new Observer<ArrayList<Owner>>() {
        @Override
        public void onChanged(ArrayList<Owner> ownerItems) {
            if (ownerItems != null) {
                adapter.setData(ownerItems);
                showLoading(false);


            } else {
                adapter.setData(ownerItems);
                showLoading(true);
            }

            owners = new ArrayList<>();
            owners.addAll(ownerItems);


        }

    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);

        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
//                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                    int idPost = Integer.parseInt(query);
                    mainViewModel.setPostSearch(idPost);
                    showLoading(true);
                    hideKeyboard();
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_change_settings){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
