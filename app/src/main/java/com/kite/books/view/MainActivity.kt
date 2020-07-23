package com.kite.books.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.kite.books.R
import com.kite.books.adapter.BookListRecycleViewAdapter
import com.kite.books.model.QueryBooks
import com.kite.books.viewmodel.QueryBookViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var queryBookViewModel: QueryBookViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestNetworkPermission()
        queryBookViewModel = ViewModelProviders.of(this).get(QueryBookViewModel::class.java)
        queryBookViewModel!!.getQueryBooks().observe(this, Observer { queryBooks ->
            queryBooks?.let {
                updateBooks(it)
            }
        })
        val booksRvLayout = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        books_rv.layoutManager = booksRvLayout
        val booksRvAdapter = BookListRecycleViewAdapter()
        books_rv.adapter = booksRvAdapter
        queryBookViewModel?.queryBooks("python", arrayListOf("id", "title", "image", "price", "publisher"))
    }

    private fun updateBooks(books: QueryBooks) {
        Log.d(TAG, "update books")
        val adapter = books_rv.adapter as? BookListRecycleViewAdapter
        adapter?.setData(books.books)
    }

    private fun requestNetworkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "permission granted")
            return
        }
        Log.d(TAG, "request network permission")
        val permissions = arrayOf(Manifest.permission.INTERNET)
        requestPermission(permissions, mInternetPermissionRequestCode)
    }

    private fun requestPermission(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            mInternetPermissionRequestCode -> {
                grantResults?.forEach {
                    if (it == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "allow use internet")
                    }
                }
            }
            else -> {

            }
        }
    }

    companion object {
        private const val mInternetPermissionRequestCode = 100
        private const val TAG = "MainActivity"
    }
}
