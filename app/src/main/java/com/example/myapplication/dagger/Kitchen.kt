package com.example.myapplication.dagger

import android.util.Log

class Kitchen(var chef: Chef, var order: String) {

    public fun isOrder():Boolean{
        if(order.isNotEmpty()){
            Log.d("MyTag", "Chef: $chef order: $order");
            return true;
        }

        return false
    }
}
