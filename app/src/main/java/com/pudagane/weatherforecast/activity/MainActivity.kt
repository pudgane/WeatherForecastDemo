package com.pudagane.weatherforecast.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pudagane.weatherforecast.R
import com.pudagane.weatherforecast.adapters.OnItemClickListener
import com.pudagane.weatherforecast.adapters.WeatherReportAdapter
import com.pudagane.weatherforecast.model.Data
import com.pudagane.weatherforecast.model.ForecastResponse
import com.pudagane.weatherforecast.network.APIService
import com.pudagane.weatherforecast.network.ApiUtils.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), OnItemClickListener {

   private var edtDestinationCity:EditText?=null
    private var progressBar:ProgressBar?=null
    private var recyclerView:RecyclerView?=null
    var forcastData = ArrayList<Data>()
    private var cityName:String?= null
    private var country:String?= null
    private var lat:String?= null
    private var long:String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initiliseUIFields()
    }

    private fun initiliseUIFields() {
        edtDestinationCity = findViewById(R.id.edtDestinationCity)
        edtDestinationCity?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                performSearch()
                hideKeyboard()
            }
            true
        }

         progressBar = findViewById(R.id.top_progressBar)
        recyclerView=findViewById(R.id.recycler_view)
        //adding a layoutmanager
        recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


    }

    private fun hideKeyboard() {
        edtDestinationCity?.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edtDestinationCity?.windowToken, 0)
        }
    }

    private fun performSearch() {
        val city = edtDestinationCity?.text.toString().trim()
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "Please enter destination city." , Toast.LENGTH_LONG).show()
            return
        }
        forcastData.removeAll(forcastData)
            //Variable declaration
            val mAPIService: APIService = apiService

        //After oncreate

        progressBar?.visibility =View.VISIBLE
            mAPIService.getReport(city,"c6ea77d9965849aba852974b9e156c22").enqueue(object : Callback<ForecastResponse> {

                override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                    progressBar?.visibility = GONE
                    if (response.isSuccessful) {
                        Log.d("", "post submitted to API." + response.body()!!)
                        forcastData = response.body()?.data as ArrayList<Data>
                        cityName=response.body()?.cityName
                        country=response.body()?.countryCode
                        lat=response.body()?.lat
                        long=response.body()?.lon
                            setAdapter()
                    }else {
                        Log.d("404","not found")
                    }
                }
                override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("LoginActivity",""+t.message)
                    progressBar?.visibility = GONE
                }
            })
    }

    private fun setAdapter() {
        //creating our adapter
        val adapter = WeatherReportAdapter(forcastData,this,this)
        //now adding the adapter to recyclerview
        recyclerView?.adapter = adapter
    }

    override fun onItemClicked(data: Data) {
        Toast.makeText(this,"Date="+data.validDate,Toast.LENGTH_LONG)
            .show()
        Log.i("Data",data.toString())

        val intent = Intent(this, DetailedActivity::class.java)
        intent.putExtra("details", data )
        intent.putExtra("city",cityName)
        intent.putExtra("country",country)
        intent.putExtra("lat",lat)
        intent.putExtra("lon",long)
        startActivity(intent)
    }


}
