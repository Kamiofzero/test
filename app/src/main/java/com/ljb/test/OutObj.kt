package com.ljb.test

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class OutObj : BaseObservable() {

    @Bindable
    var inObj: InObj? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.inObj)
        }
}


class InObj : BaseObservable() {
    @Bindable
    var i: Int = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.i)
        }

    @Bindable
    var j: Int = 2
        set(value) {
            field = value
            notifyPropertyChanged(BR.j)
        }
}