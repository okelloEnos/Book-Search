package com.okellosoftwarez.booklistactivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    ArrayList<Book> books;
    public BookAdapter(ArrayList<Book> book) {
        this.books = book;
    }

    @NonNull

    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.book_item,parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public  class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvAuthor;
        TextView tvDate;
        TextView tvPublish;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvDate = itemView.findViewById(R.id.tv_publishedDate);
            tvPublish = itemView.findViewById(R.id.tv_publisher);
            itemView.setOnClickListener(this);
        }

        public void bind(Book book){
            tvTitle.setText(book.title);
//            String Authors = "";
//            int i = 0;
//            for (String author:book.authors) {
//                Authors+=author;
//                i++;
//
//                if (i<book.authors.length){
//                    Authors+=", ";
//                }
                
//            }
            tvAuthor.setText(book.authors);
            tvDate.setText(book.publishDate);
            tvPublish.setText(book.publisher);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Book selectedBook = books.get(position);
            Intent detailIntent = new Intent(view.getContext(), BookDetail.class);
            detailIntent.putExtra("Book", selectedBook);
            view.getContext().startActivity(detailIntent);
        }
    }

}
