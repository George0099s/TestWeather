package com.enterprise.test.data.network.manager

import android.annotation.SuppressLint
import com.enterprise.test.data.network.api.API
import com.enterprise.test.data.network.pojo.Picture
import io.reactivex.Observable


class PictureNetworkManager {

    @SuppressLint("CheckResult")
    fun getPics(page: Int, limit: Int): Observable<Picture> {
       return API.createPicture().getPics(page, limit)
    }
}