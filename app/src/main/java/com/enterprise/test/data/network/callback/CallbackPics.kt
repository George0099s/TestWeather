package com.enterprise.test.data.network.callback

import com.enterprise.test.data.network.pojo.Picture


interface CallbackPics {
    fun onPicsLoaded(list: Picture)
}
