package com.nedushny.test_task

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kotlin.math.roundToInt

class GridSpacingItemDecoration(
    private val context: Context,
    private val spanCount: Int = 2,
    private val spacing: Int = 4,
    private val includeEdge: Boolean = false
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val dp = context.resources.displayMetrics.density
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        val itemCount = parent.adapter?.itemCount ?: 0

        if (includeEdge) {
            if (itemCount % 2 == 0) {
                when (position) {
                    0, 1 -> outRect.top = (spacing * 2 * dp).roundToInt()
                    itemCount.minus(1), itemCount.minus(2) ->
                        outRect.bottom = (spacing * 20 * dp).roundToInt()
                }
            } else {
                when (position) {
                    0, 1 -> outRect.top = (spacing * 2 * dp).roundToInt()
                    itemCount.minus(1) ->
                        outRect.bottom = (spacing * 20 * dp).roundToInt()
                }
            }
            outRect.left =
                ((spacing - column * spacing / spanCount) * dp).roundToInt() // spacing - column * ((1f / spanCount) * spacing)
            outRect.right =
                ((column + 1) * spacing / spanCount * dp).roundToInt()// (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = (spacing * dp).roundToInt()
            }
            outRect.bottom = (spacing * dp).roundToInt() // item bottom
        } else {
            if (itemCount % 2 == 0) {
                when (position) {
                    0, 1 -> outRect.top = (spacing * 2 * dp).roundToInt()
                    itemCount.minus(1), itemCount.minus(2) ->
                        outRect.bottom = (spacing * 20 * dp).roundToInt()
                }
            } else {
                when (position) {
                    0, 1 -> outRect.top = (spacing * 2 * dp).roundToInt()
                    itemCount.minus(1) ->
                        outRect.bottom = (spacing * 20 * dp).roundToInt()
                }
            }
            outRect.left =
                (dp * column * spacing / spanCount).roundToInt() // column * ((1f / spanCount) * spacing)
            outRect.right =
                ((spacing - (column + 1) * spacing / spanCount) * dp).roundToInt() // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = (spacing * dp).roundToInt() // item top
            }
        }
    }
}