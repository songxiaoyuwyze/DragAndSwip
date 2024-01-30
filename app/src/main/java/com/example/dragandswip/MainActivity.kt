package com.example.dragandswip

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

	inner class Item(var content :String = "",var type:Int = 1,var color:Int = 0)
	companion object {
		private const val ITEM_TYPE_1 = 1
		private const val ITEM_TYPE_2 = 2
		private const val ITEM_TYPE_3 = 3
		private const val ITEM_TYPE_4 = 4
	}

	val datas = arrayListOf<Item>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
		datas.add(Item("1",1, getRandomColor()))
		datas.add(Item("2",2, getRandomColor()))
		datas.add(Item("3",1, getRandomColor()))
		datas.add(Item("4",2, getRandomColor()))
		datas.add(Item("5",2, getRandomColor()))
		datas.add(Item("6",2, getRandomColor()))
		datas.add(Item("7",1, getRandomColor()))
		datas.add(Item("8",1, getRandomColor()))
		datas.add(Item("9",1, getRandomColor()))
		datas.add(Item("10",2, getRandomColor()))
		datas.add(Item("11",1, getRandomColor()))
		datas.add(Item("12",2, getRandomColor()))
		datas.add(Item("13",1, getRandomColor()))
		datas.add(Item("14",1, getRandomColor()))

		val recyclerView = findViewById<RecyclerView>(R.id.recycler)
		val adapter = CustomAdapter()
		recyclerView.adapter  = adapter
		val layoutManager = GridLayoutManager(this, 2)
		// 设置每个 item 的跨度
		layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
			override fun getSpanSize(position: Int): Int {
				val adapter = recyclerView.adapter as CustomAdapter
				return when (adapter.getItemViewType(position)) {
					ITEM_TYPE_1 -> 2 // item1 占满一列
					ITEM_TYPE_2 -> 1
					else -> 1
				}
			}
		}
		recyclerView.layoutManager = layoutManager
		val itemTouchHelper = ItemTouchHelper(CustomItemTouchHelper())
		itemTouchHelper.attachToRecyclerView(recyclerView)
		adapter.notifyDataSetChanged()

    }



	inner class CustomAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),ItemMoveCallback {


		override fun getItemViewType(position: Int): Int {
			// 根据位置返回不同的 item 类型
			return when (datas[position].type) {
				1 -> ITEM_TYPE_1
				2 -> ITEM_TYPE_2
				else -> ITEM_TYPE_1
			}
		}

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
			val inflater = LayoutInflater.from(parent.context)
			val itemView = when (viewType) {
				ITEM_TYPE_1 -> inflater.inflate(R.layout.item_type_1, parent, false)
				ITEM_TYPE_2 -> inflater.inflate(R.layout.item_type_1, parent, false)
				ITEM_TYPE_3 -> inflater.inflate(R.layout.item_type_1, parent, false)
				else -> inflater.inflate(R.layout.item_type_1, parent, false)
			}
			return ViewHolder(itemView)
		}

		override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
			// 在这里设置每个 item 的数据
			// 根据不同的 item 类型，可以使用不同的 ViewHolder 进行设置
			if(holder is ViewHolder){
				holder.tv.text = datas[position].content
				holder.tv.setBackgroundColor(datas[position].color)
			}
		}

		override fun getItemCount(): Int {
			// 返回列表的总项数
			return datas.size // 这里只是一个示例，你可以根据实际需求进行调整
		}

		override fun onItemMove(fromPosition: Int, toPosition: Int) {
			if (fromPosition < toPosition) {
				for (i in fromPosition until toPosition) {
					Collections.swap(datas, i, i + 1)
				}
			} else {
				for (i in fromPosition downTo toPosition + 1) {
					Collections.swap(datas, i, i - 1)
				}
			}
			notifyItemMoved(fromPosition, toPosition)
		}
	}


	// 定义 ViewHolder 类型
	private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),ItemHolderMoveCallback {
		// ViewHolder 的布局中的视图
		// ...
		val tv:TextView = itemView.findViewById(R.id.tv)
		override fun onItemHolderMoveStart() {
		}

		override fun onItemHolderMoveEnd() {
		}
	}
}
fun getRandomColor(): Int {
	val red = Random.nextInt(256)
	val green = Random.nextInt(256)
	val blue = Random.nextInt(256)
	return android.graphics.Color.rgb(red, green, blue)
}


