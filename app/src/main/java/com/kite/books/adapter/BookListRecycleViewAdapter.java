package com.kite.books.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kite.books.R;
import com.kite.books.model.Book;
import com.kite.books.databinding.ItemBookBinding;
import com.kite.books.viewmodel.ItemBookViewModel;

import java.util.List;

public class BookListRecycleViewAdapter extends RecyclerView.Adapter<BookListRecycleViewAdapter.BookViewHolder> {
    private List<Book> mData;

    public BookListRecycleViewAdapter() {
    }

    public void setData(List<Book> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_book, parent, false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if (holder.bookBinding.getBookViewModel() == null) {
            holder.bookBinding.setBookViewModel(new ItemBookViewModel());
        }
        holder.bookBinding.getBookViewModel().updateBook(mData.get(position), holder.bookBinding.image, holder.bookBinding.getRoot().getContext());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        public ItemBookBinding bookBinding;

        public BookViewHolder(@NonNull ItemBookBinding binding) {
            super(binding.getRoot());
            bookBinding = binding;
        }
    }
}
