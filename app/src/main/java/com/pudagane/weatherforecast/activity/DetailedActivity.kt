package com.pudagane.weatherforecast.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pudagane.weatherforecast.R
import com.pudagane.weatherforecast.model.Data


class DetailedActivity : AppCompatActivity() {
   private var city:String? =null
    private var item:Data?=null
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

         item = intent.getParcelableExtra("details" ) as Data
         city = intent.getStringExtra("city") as String
        val country =  intent.getStringExtra("country") as String
       val lat:String= intent.getStringExtra("lat") as String
       val long:String= intent.getStringExtra("lon") as String
        Log.d("DetailedActivity",item.toString())


        val tvCity = findViewById<TextView>(R.id.valueCity)
        tvCity.text = city
        val tvCountry = findViewById<TextView>(R.id.valueCountry)
        tvCountry.text=country

        val ivLogo = findViewById<ImageView>(R.id.valuesWeather)
        val maxTemp= findViewById<TextView>(R.id.tvMaxTemp)
        val minTemp= findViewById<TextView>(R.id.tvMinTemp)

        val date= findViewById<TextView>(R.id.valuesDate)
        date.text=item?.validDate

        val tvlat= findViewById<TextView>(R.id.valueLat)
        tvlat.text=lat
        val tvlon= findViewById<TextView>(R.id.valueLon)
        tvlon.text=long

        maxTemp.text=item?.maxTemp.toString()
        minTemp.text = item?.minTemp.toString()
        Glide.with(this)
            .load("https://www.weatherbit.io/static/img/icons/"+item?.weather?.icon+".png")
            .into(ivLogo)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_share -> {
            shareDetails()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    private fun shareDetails(){
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Weather Forecast for city:${city}")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, item.toString())
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }


}
