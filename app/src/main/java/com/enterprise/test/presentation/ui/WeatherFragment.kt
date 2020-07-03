package com.enterprise.test.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.enterprise.test.R
import com.enterprise.test.data.network.pojo.Weather.Weather
import com.enterprise.test.presentation.utils.Validator
import com.enterprise.test.presentation.viewmodel.WeatherViewModel
import com.enterprise.test.viewstate.WeatherIntent
import com.enterprise.test.viewstate.WeatherViewState
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.weather_fragment.view.*
import java.util.regex.Pattern


class WeatherFragment : Fragment() {

    @BindView(R.id.weather_progress) lateinit  var progress: ProgressBar

    private val validator by lazy{
        Validator()
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weather_fragment, container, false)
        ButterKnife.bind(this, view)
        view.login_btn.setOnClickListener {
            if (validator.validateEmail(view.login.text.toString())
                && validator.validatePass(view.password_ed.text.toString())) {
                getWeather()
                view.password.error = null
                view.textInputLayout.error = null
            } else {
                if (!validator.validateEmail(view.login.text.toString())
                ) {
                    view.login.requestFocus()
                    view.textInputLayout.error = getString(R.string.wrong_login)
                } else
                    view.textInputLayout.error = null

                if (!validator.validatePass(view.password_ed.text.toString())) {
                    view.password_ed.requestFocus()
                    view.password.error = getString(R.string.wrong_password)
                } else view.password.error = null

            }
        }
        return view
    }

    private fun getWeather() {
        viewModel.weatherIntentLiveData.value = WeatherIntent.GetWeather(498817, "ru")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        viewModel.weatherLiveData.observe(viewLifecycleOwner, setWeather(view!!))
        viewModel.weatherIntentLiveData.observe(viewLifecycleOwner, intents())
    }

    private fun intents() = Observer<WeatherIntent> {
        when (it) {
            is WeatherIntent.GetWeather -> {
                viewModel.getWeather(it.id, it.lang)
            }
        }
    }


    private fun setWeather(view: View) = Observer<WeatherViewState> {
        when (it) {
            is WeatherViewState.WeatherLoadingState -> {
                progress.visibility = View.VISIBLE
            }
            is WeatherViewState.WeatherLoadedState -> {
                progress.visibility = View.GONE
                val snackbar: Snackbar = Snackbar.make(view, getString(
                    R.string.weather_string,
                    it.weather.name,
                    it.weather.main.temp,
                    it.weather.weather[0].description,
                    it.weather.main.humidity
                ), Snackbar.LENGTH_LONG)
                val snackbarView: View = snackbar.view
                val textView =
                    snackbarView.findViewById<View>(R.id.snackbar_text) as TextView
                textView.maxLines = 5
                snackbar.show()
            }
            is WeatherViewState.WeatherErrorState -> {
                Snackbar.make(view, it.message, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
