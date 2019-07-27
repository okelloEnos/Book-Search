package com.okellosoftwarez.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
//import android.widget.SearchView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookList extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ProgressBar mLoading;
    private RecyclerView rvBooks;
    private LinearLayoutManager bookLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);


        mLoading = findViewById(R.id.progressBar);
        rvBooks = findViewById(R.id.rv_books);
        bookLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvBooks.setLayoutManager(bookLayoutManager);
        getSupportActionBar().setTitle("Books");
        try {
            URL bookUrl = ApiUtil.buildURL("Cooking");
            new BookQueryTask().execute(bookUrl);
          }
        catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            URL bookUrl = ApiUtil.buildURL(query);
            new BookQueryTask().execute(bookUrl);
        }
        catch (Exception e){
            Log.d("error",e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public class BookQueryTask extends AsyncTask <URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String results = null;
            try {
                results = ApiUtil.getJson(searchUrl);
            }
            catch (IOException e){
                Log.d("error", e.getMessage());
            }
            return results;
        }

        @Override
        protected void onPostExecute(String results) {
            TextView error_message = findViewById(R.id.tv_error);

            mLoading.setVisibility(View.INVISIBLE);
            if (results == null){

                rvBooks.setVisibility(View.INVISIBLE);
                error_message.setVisibility(View.VISIBLE);
            }
            else {
                rvBooks.setVisibility(View.VISIBLE);
                error_message.setVisibility(View.INVISIBLE);
            }
            ArrayList<Book> books = ApiUtil.getBooksFromJson(results);

            BookAdapter Adapter = new BookAdapter(books);
            rvBooks.setAdapter(Adapter);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
        }
    }
}
