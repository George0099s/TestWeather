package com.enterprise.test.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enterprise.test.data.App
import com.enterprise.test.data.network.callback.CallbackPics
import com.enterprise.test.data.network.pojo.Picture
import com.enterprise.test.di.Component
import com.enterprise.test.di.DaggerComponent
import com.enterprise.test.domain.Repository
import com.enterprise.test.viewstate.PictureIntent
import com.enterprise.test.viewstate.PictureViewState
import javax.inject.Inject

class PictureViewModel : ViewModel(), CallbackPics {
    var pics: MutableLiveData<PictureViewState> = MutableLiveData()
    var events: MutableLiveData<PictureIntent> = MutableLiveData()


    @Inject
    lateinit var repository: Repository


    init {
        App.instance.component.inject(this)
        pics.value = PictureViewState.PictureNoItemsState
    }

    fun loadPicture(page: Int, limit: Int) {
        pics.value = PictureViewState.PictureLoadingState
        repository.getPics(page, limit, this)
    }

    override fun onPicsLoaded(list: Picture) {
        pics.postValue(PictureViewState.PictureLoadedState(list))
    }

    override fun onPicsLoadedFailure(errorMessage: String) {
        pics.postValue(PictureViewState.PictureErrorState(errorMessage))
    }
}
