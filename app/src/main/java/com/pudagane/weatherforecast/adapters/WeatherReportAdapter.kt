package com.pudagane.weatherforecast.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pudagane.weatherforecast.R
import com.pudagane.weatherforecast.model.Data
import java.util.ArrayList

class WeatherReportAdapter(val items : ArrayList<Data>, val context: Context, val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<WeatherReportAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherReportAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_list_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: WeatherReportAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position],context,itemClickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return items.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: Data,context: Context,clickListener: OnItemClickListener) {
            val tvDate = itemView.findViewById(R.id.tvDate) as TextView
            val minTemp  = itemView.findViewById(R.id.minTemp) as TextView
            val maxTemp  = itemView.findViewById(R.id.maxTemp) as TextView

            val ivLogo = itemView.findViewById(R.id.ivLogo) as ImageView
            tvDate.text = data.validDate
            maxTemp.text=data.maxTemp.toString()
            minTemp.text = data.minTemp.toString()
            Glide.with(context)
                .load("https://www.weatherbit.io/static/img/icons/"+data.weather.icon+".png")
                .into(ivLogo);


            itemView.setOnClickListener {
                clickListener.onItemClicked(data)
            }
        }


    }
}

interface OnItemClickListener{
    fun onItemClicked(data: Data)
}