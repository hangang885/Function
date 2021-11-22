package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.databinding.ActivityNodeBinding
import com.example.myapplication.retrofit2.RetrofitClient
import com.example.myapplication.retrofit2.RetrofitInterface
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NodeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNodeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.selectBtn.setOnClickListener {

            var jsonObject:JsonObject = JsonObject()
            jsonObject.addProperty("test","test")

            RetrofitClient().getResponse()!!.create(RetrofitInterface::class.java).select().enqueue(object :
                Callback<JsonObject> {

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful){
                        Log.d("Han_Response",response.body()!!.get("rows").toString())
                    }
                    else{
                        Log.d("Han_error",response.toString())
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("Han_onFailure",t.message.toString())
                }

            })

        }
        binding.insertBtn.setOnClickListener {

            var jsonObject:JsonObject = JsonObject()
            jsonObject.addProperty("id","88")
            jsonObject.addProperty("pw","1234")
            jsonObject.addProperty("name","김강")
            jsonObject.addProperty("age","44")


            RetrofitClient().getResponse()!!.create(RetrofitInterface::class.java).insert(jsonObject).enqueue(object :
                Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        Log.d("Han_insert_Response",response.body().toString())
                    }
                    else{
                        Log.d("Han_insert_error",response.toString())
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("Han_insert_onFailure",t.message.toString())
                }

            })

        }
    }
}