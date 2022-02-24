package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import org.jetbrains.anko.padding

class CustomGalleryAdapter(private val context: Context, uriArr: ArrayList<String>): BaseAdapter() {

    private var count = 0

    private var items = ArrayList<String>()
    init {
        this.items = uriArr
    }



    private var  sendItems = arrayListOf<String>()



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView


        if (convertView == null ){


            imageView = ImageView(context)
            val display = context.resources.displayMetrics
            imageView.padding = 2
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.layoutParams = LinearLayout.LayoutParams(display.widthPixels/4, display.widthPixels/4)
            Glide.with(context).load(items[position]).into(imageView)
        }
        else{
            imageView = convertView as ImageView
        }

        if(position < items.size){
            Glide.with(context).load(items[position]).into(imageView)
            return imageView
        }



        imageView.setOnClickListener {
//                var jsonParser: JsonParser = JsonParser()

            if (imageView.colorFilter != null) {
                imageView.colorFilter = null
                sendItems.remove(items[position])
                Log.d("sendItems", sendItems.toString())
                --count
            } else {
                if (count < 5) {
                    colorFilter(imageView)
                    Log.d("items[position]", items[position])
                    sendItems.add(items[position])
                    Log.d("sendItems", sendItems.toString())
                    Log.d("sendItems", sendItems.toString())
                    count++

                }else {
                    Toast.makeText(context, "5개 까지만 선택이 가능합니다.", Toast.LENGTH_SHORT).show()
                }
                val sendItemValue = items[position]
                Log.d("sendItemValue", sendItemValue)
            }


            Log.d("CountValue", count.toString())
        }


        return imageView

    }

    override fun getItem(position: Int): Any  = items[position]

    override fun getItemId(position: Int): Long{
        return position.toLong()
    }
    override fun getCount(): Int = items.size



    private fun colorFilter(imageview: ImageView){
        imageview.setColorFilter(Color.parseColor("#B3000000"))
    }



}






