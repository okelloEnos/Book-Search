package com.okellosoftwarez.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText etTitle,etAuthor,etPublisher,etIsbn;
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etPublisher = findViewById(R.id.etPublisher);
        etIsbn = findViewById(R.id.etIsbn);
        final Button advanceButton = findViewById(R.id.btnSearch);
        advanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString().trim();
                String author = etAuthor.getText().toString().trim();
                String publisher = etPublisher.getText().toString().trim();
                String isbn = etIsbn.getText().toString().trim();

                if (title.isEmpty() && author.isEmpty() && publisher.isEmpty() && isbn.isEmpty()){
                    String message = getString(R.string.invalid_search);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else {
                    URL queryURL = ApiUtil.buildURL(title, author, publisher, isbn);
                    //Shared Preferences
                    Context context = getApplicationContext();
                    int position = spUtil.getSharedPrefInt(context, spUtil.POSITION);
                    if (position == 0 || position == 1){
                        position = 1 ;
                    }
                    else {
                        position ++;
                    }
                    String key = spUtil.QUERY + String.valueOf(position);
                    String value = title + "," + author + "," + publisher + "," + isbn;
                    spUtil.setSharedPrefString(context, key, value);
                    spUtil.setSharedPrefInt(context, spUtil.POSITION, position);

                    Intent backSearch = new Intent(getApplicationContext(), BookList.class);
                    backSearch.putExtra("Query", queryURL.toString());
                    startActivity(backSearch);
                }
            }
        });
    }
}
