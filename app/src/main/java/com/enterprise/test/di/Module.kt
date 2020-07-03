package com.enterprise.test.di

import com.enterprise.test.data.network.manager.PictureNetworkManager
import com.enterprise.test.data.network.manager.WeatherNetworkManager
import com.enterprise.test.domain.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Module {

    @Singleton
    @get:Provides
        val repository: Repository
            get() = Repository()

    @Singleton
    @get:Provides
        val networkManager: PictureNetworkManager
            get() = PictureNetworkManager()
   @Singleton
    @get:Provides
        val weatherNetworkManager: WeatherNetworkManager
            get() = WeatherNetworkManager()

}