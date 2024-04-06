package com.ljb.test

import androidx.databinding.ObservableField

class Item {

    var oi = ObservableField<Int>()
    var ob = ObservableField<Boolean>()
    var os = ObservableField<String>()

    constructor(
        i: Int = 1, b: Boolean = false, s: String = "hello"
    ) {
        this.oi.set(i)
        this.ob.set(b)
        this.os.set(s)
    }

    fun getS(): String? {
        return os.get()
    }

    fun setS(s: String) {
        os.set(s)
    }
}