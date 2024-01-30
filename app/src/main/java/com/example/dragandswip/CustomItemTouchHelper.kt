package com.example.dragandswip

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class CustomItemTouchHelper: ItemTouchHelper.Callback() {
	override fun getMovementFlags(
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder
	): Int {
		return if (recyclerView.layoutManager is GridLayoutManager || recyclerView.layoutManager is StaggeredGridLayoutManager) {
			val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
				ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
			val swipeFlags = 0
			makeMovementFlags(dragFlags, swipeFlags)
		} else {
			val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
			val swipeFlags = 0
			makeMovementFlags(dragFlags, swipeFlags)
		}
	}

	override fun onMove(
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
		target: RecyclerView.ViewHolder
	): Boolean {
		//拖拽中的viewHolder的Position
		val fromPosition = viewHolder.getAdapterPosition()
		//当前拖拽到的item的viewHolder
		val toPosition = target.getAdapterPosition()
		if (recyclerView.adapter is ItemMoveCallback) {
			(recyclerView.adapter as ItemMoveCallback?)!!.onItemMove(fromPosition, toPosition)
		}
		return true
	}

	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
	}

	override fun isLongPressDragEnabled(): Boolean {
		return true
	}

	override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
		super.onSelectedChanged(viewHolder, actionState)
		if (actionState !== ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemHolderMoveCallback) {
			(viewHolder as ItemHolderMoveCallback).onItemHolderMoveStart()
		}
	}

	override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
		super.clearView(recyclerView, viewHolder)
		if (viewHolder is ItemHolderMoveCallback) {
			(viewHolder as ItemHolderMoveCallback).onItemHolderMoveEnd()
		}
	}

}


interface ItemMoveCallback {
	fun onItemMove(fromPosition: Int, toPosition: Int)
}


interface ItemHolderMoveCallback {
	fun onItemHolderMoveStart()
	fun onItemHolderMoveEnd()
}
