package com.enterprise.test.presentation.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.enterprise.test.R
import com.enterprise.test.data.network.api.API
import com.enterprise.test.data.network.pojo.Picture
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val pictureFragment by lazy{
        PictureFragment()
    }

    private val weatherFragment by lazy{
        WeatherFragment()
    }
    var active: Fragment = pictureFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.main_container, pictureFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_container, weatherFragment)
            .hide(weatherFragment).commit()
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_pics -> {
                    if (active != pictureFragment) {
                        supportFragmentManager.beginTransaction().hide(active).show(pictureFragment)
                            .commit()
                        active = pictureFragment
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_weather -> {
                    if (active != weatherFragment) {
                        supportFragmentManager.beginTransaction().hide(active).show(weatherFragment)
                            .commit()
                        active = weatherFragment
                    }
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
