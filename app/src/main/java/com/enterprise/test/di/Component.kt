package com.enterprise.test.di

import com.enterprise.test.domain.Repository
import com.enterprise.test.presentation.viewmodel.PictureViewModel
import com.enterprise.test.presentation.viewmodel.WeatherViewModel
import dagger.Component

@Component(modules = [Module::class])
interface Component {
    fun inject(vm: WeatherViewModel)
    fun inject(vm: PictureViewModel)
    fun inject(repo: Repository)
}