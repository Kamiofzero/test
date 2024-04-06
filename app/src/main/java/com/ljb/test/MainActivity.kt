package com.ljb.test

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.ljb.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var item = Item()
    var list = CustomObservableArrayList<String>()
    var list1 = ObservableArrayList<String>()
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        dataBinding.list = list
//        dataBinding.list1 = list1
//        dataBinding.item = item

        dataBinding.button.setOnClickListener {
            list1.add("1")
        }
        dataBinding.button2.setOnClickListener {
            if (list1.size > 0) list1.removeAt(list1.size - 1)
        }
        dataBinding.button3.setOnClickListener {
            list1.clear()
        }
        dataBinding.button4.setOnClickListener {
            item.setS("hello")
        }
        dataBinding.button5.setOnClickListener {
            item.setS("world")
        }
    }

}

@BindingAdapter("m")
fun m(textView: TextView, list: List<Any>?) {
    Log.i("tag", "m")
}

@BindingAdapter("i")
fun i(textView: TextView, i: Int?) {
    Log.i("tag", "i")
}