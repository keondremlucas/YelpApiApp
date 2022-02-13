package com.example.yelpclone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "nt-8xIiNM4jE7gOB0d_MehHrl8V_ewCmSPWQKffh6DZnHEUsDKqyiEEYb0c4cC67zxmxPwslTdxK3F8o_XGT_buE_Jk2FleKiwMD7cnqGjTkFR9unRFU1OfZ4UGDYHYx"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val restaurants = mutableListOf<Restaurant>()
        val adapter = RestaurantsAdapter(this, restaurants)
        val builder = AlertDialog.Builder(this)


        var rv = findViewById<RecyclerView>(R.id.restaurantsview)
        rv.visibility = View.INVISIBLE
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)

        button2.setOnClickListener{
            reset(this)
        }
        button.setOnClickListener {
            rv.visibility = View.VISIBLE
            adapter.notifyDataSetChanged()
            var searchn = findViewById<EditText>(R.id.foodtype)
            var searchl = findViewById<EditText>(R.id.foodlocation)
            if(searchn.text == null || searchl.text == null)
            {
                builder.setTitle("Missing a Term")
                builder.setMessage("You are missing a food or location term, please enter them.")
                builder.setPositiveButton("Ok", null)
                val dialog : AlertDialog = builder.create()
                dialog.show()

            }

            if(searchn.text.toString() == "" || searchl.text.toString() == "")
            {
                builder.setTitle("Missing a Term")
                builder.setMessage("You are missing a food or location term, please enter them.")
                builder.setPositiveButton("Ok", null)
                val dialog : AlertDialog = builder.create()
                dialog.show()

            }


            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            val yelpService = retrofit.create(YelpService::class.java)
            yelpService.searchbusinesses("Bearer $API_KEY", searchn.text.toString(), searchl.text.toString()).enqueue(object : Callback<YelpData> {
                override fun onResponse(call: Call<YelpData>, response: Response<YelpData>) {
                    Log.i(TAG, "onResponse $response")
                    val body = response.body()
                    if (body == null) {
                        Log.w(TAG, "No valid response")
                        return
                    }
                    restaurants.addAll(body.restaurants)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<YelpData>, t: Throwable) {

                    Log.i(TAG, "onFailure $t")
                }

            })
        }
        adapter.notifyDataSetChanged()
    }
    fun reset(view: MainActivity){
        val myintent = Intent(this, MainActivity::class.java)
        startActivity(myintent)
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

}
