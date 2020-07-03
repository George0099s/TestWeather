package com.enterprise.test.viewstate

import com.enterprise.test.data.network.pojo.PictureItem

sealed class PictureViewState{
    object PictureLoadingState: PictureViewState()
    object PictureNoItemsState: PictureViewState()
    class PictureLoadedState(val pictures: List<PictureItem>): PictureViewState()
    class PictureErrorState(val message: String): PictureViewState()
}

sealed class PictureIntent(){
    class PictureScroll(val page: Int, val limit: Int) : PictureIntent()
}