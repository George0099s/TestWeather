package com.enterprise.test.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.enterprise.test.R
import com.enterprise.test.data.network.pojo.Weather.Weather
import com.enterprise.test.presentation.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.weather_fragment.view.*
import java.util.regex.Pattern


class WeatherFragment : Fragment() {

    private val PASSWORD_VALIDATOR = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})"

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weather_fragment, container, false)
        val pattern = Pattern.compile(PASSWORD_VALIDATOR)

        view.login_btn.setOnClickListener { v ->
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(view.login.text.toString())
                    .matches() && pattern.matcher(view.password_ed.text.toString()).matches()
            ) {
                getWeather()
                view.password.error = null
                view.textInputLayout.error = null
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(view.login.text.toString())
                        .matches()
                ) {
                    view.login.requestFocus()
                    view.textInputLayout.error = getString(R.string.wrong_login)
                } else
                    view.textInputLayout.error = null

                if (!pattern.matcher(view.password_ed.text.toString()).matches()) {
                    view.password_ed.requestFocus()
                    view.password.error = getString(R.string.wrong_password)
                } else view.password.error = null

            }
        }
        return view
    }

    fun getWeather() {
        viewModel.getWeather(498817, "ru")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        viewModel.weatherLiveData.observe(viewLifecycleOwner, setWeather(view!!))

    }



    private fun setWeather(view: View) = Observer<Weather> {
        val snackbar: Snackbar = Snackbar.make(view, getString(
            R.string.weather_string,
            it.name,
            it.main.temp.toString(),
            it.weather[0].description,
            it.main.humidity.toString()
        ), Snackbar.LENGTH_LONG)
        val snackbarView: View = snackbar.view
        val textView =
            snackbarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.maxLines = 5
        snackbar.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }
}
