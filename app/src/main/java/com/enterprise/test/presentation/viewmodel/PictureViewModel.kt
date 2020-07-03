package com.enterprise.test.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enterprise.test.data.App
import com.enterprise.test.domain.Repository
import com.enterprise.test.viewstate.PictureIntent
import com.enterprise.test.viewstate.PictureViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PictureViewModel : ViewModel() {
    var pics: MutableLiveData<PictureViewState> = MutableLiveData()
    var events: MutableLiveData<PictureIntent> = MutableLiveData()


    @Inject
    lateinit var repository: Repository


    init {
        App.instance.component.inject(this)
        pics.value = PictureViewState.PictureNoItemsState
    }

    @SuppressLint("CheckResult")
    fun loadPicture(page: Int, limit: Int) {
        pics.value = PictureViewState.PictureLoadingState
        repository.getPics(page, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            pics.postValue(PictureViewState.PictureLoadedState(it))
        }, {
            pics.postValue(PictureViewState.PictureErrorState(it.localizedMessage))
        })
    }
}
