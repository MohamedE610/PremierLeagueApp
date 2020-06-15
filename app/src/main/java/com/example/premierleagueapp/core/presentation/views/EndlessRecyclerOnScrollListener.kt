package com.example.premierleagueapp.core.presentation.views

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * class that handle load more items in recycler view
 *
 * @property linearLayoutManager LinearLayoutManager
 * @constructor
 *
 */
abstract class EndlessRecyclerOnScrollListener(
    private val linearLayoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    var isLoadingMore = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = linearLayoutManager.itemCount
        val lastVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
        if (!isLoadingMore && lastVisibleItemPosition == totalItemCount - 1) {
            isLoadingMore = true
            onLoadMore()
            Log.d("onLoadMore", "called")
        }
    }

    abstract fun onLoadMore()
}
