package com.enterprise.test.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enterprise.test.data.App
import com.enterprise.test.data.network.callback.CallbackPics
import com.enterprise.test.data.network.pojo.Picture
import com.enterprise.test.data.network.pojo.PictureItem
import com.enterprise.test.domain.Repository
import javax.inject.Inject

class PictureViewModel : ViewModel(), CallbackPics {
    var pics: MutableLiveData<Picture> = MutableLiveData()

    @Inject lateinit var repository: Repository

    init {
        App.instance.component.inject(this)
    }
    fun loadPicture(page: Int, limit: Int){
        repository.getPics(page,limit,this)
    }

    override fun onPicsLoaded(list: Picture) {
        pics.postValue(list)
    }


}
