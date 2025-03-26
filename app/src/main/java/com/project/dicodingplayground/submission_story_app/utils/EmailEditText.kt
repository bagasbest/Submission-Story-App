package com.project.dicodingplayground.submission_story_app.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.project.dicodingplayground.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EmailEditText : TextInputEditText {
    var isValid: Boolean = false
        private set

    constructor(context: Context) : super(context) {
        initValidation()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initValidation()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initValidation()
    }


    fun initValidation() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                validateEmail(p0.toString())
            }

            override fun afterTextChanged(p0: Editable) {}

        })
    }

    private fun getTextInputLayout(): TextInputLayout? {
        var parentView = parent
        while (parentView != null && parentView !is TextInputLayout) {
            parentView = parentView.parent
        }
        return parentView
    }

    private fun validateEmail(email: String) {
        // Get the parent TextInputLayout.
        val parentLayout = getTextInputLayout()
        when {
            email.isEmpty() -> {
                parentLayout?.error = context.getString(R.string.email_must_be_filled)
                isValid = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                parentLayout?.error = context.getString(R.string.email_not_valid)
                isValid = false
            }
            else -> {
                parentLayout?.error = null
                isValid = true
            }
        }
    }
}