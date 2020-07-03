package com.enterprise.test.presentation.utils

import com.enterprise.test.R
import kotlinx.android.synthetic.main.weather_fragment.view.*
import java.util.regex.Pattern

class Validator {
    private val PASSWORD_VALIDATOR = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})"
    private val pattern = Pattern.compile(PASSWORD_VALIDATOR)

    fun validateEmail(email: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePass(pass: String): Boolean{
        return pattern.matcher(pass).matches()
    }


//    fun validateData(email: String, pass: String): Boolean{
//        if (validateEmail(email)
//            && validatePass(pass)) {
//            getWeather()
//            view.password.error = null
//            view.textInputLayout.error = null
//            return true
//        } else {
//            if (!validator.validateEmail(view.login.text.toString())
//            ) {
//                view.login.requestFocus()
//                view.textInputLayout.error = getString(R.string.wrong_login)
//            } else
//                view.textInputLayout.error = null
//
//            if (!validator.validatePass(view.password_ed.text.toString())) {
//                view.password_ed.requestFocus()
//                view.password.error = getString(R.string.wrong_password)
//            } else view.password.error = null
//        }
//        return true
//    }
}