package com.enterprise.test.presentation.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.enterprise.test.R
import com.enterprise.test.data.network.NetworkState
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

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
        if(!NetworkState.getInstance(this).isOnline)
            Toast.makeText(this, R.string.network_error, Toast.LENGTH_LONG).show()
        addFragment(pictureFragment)
        addFragment(weatherFragment)

        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_pics -> {
                hideShowFragment(pictureFragment)
                return true
            }
            R.id.menu_weather -> {
                hideShowFragment(weatherFragment)
                return true
            }
        }
        return false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.main_container, fragment).hide(fragment).show(active).commit()
    }

    private fun hideShowFragment(fragment: Fragment) {
        if (active != fragment) {
            supportFragmentManager.beginTransaction().hide(active).show(fragment)
                .commit()
            active = fragment
        }
    }

}
