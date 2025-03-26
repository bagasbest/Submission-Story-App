package com.project.dicodingplayground.submission_story_app.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.project.dicodingplayground.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PasswordEditText : TextInputEditText {

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

    private fun initValidation() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s?.toString() ?: "")
            }
        })
    }

    private fun getTextInputLayout(): TextInputLayout? {
        var parentView = parent
        while (parentView != null && parentView !is TextInputLayout) {
            parentView = parentView.parent
        }
        return parentView
    }

    private fun validatePassword(password: String) {
        // Get the parent TextInputLayout.
        val parentLayout = getTextInputLayout()
        when {
            password.isEmpty() -> {
                parentLayout?.error = context.getString(R.string.password_must_be_filled)
                // Hide the password toggle when error is present.
                parentLayout?.endIconMode = TextInputLayout.END_ICON_NONE
                isValid = false
            }
            password.length < 8 -> {
                parentLayout?.error = context.getString(R.string.password_must_be_more_than_8_characters)
                parentLayout?.endIconMode = TextInputLayout.END_ICON_NONE
                isValid = false
            }
            else -> {
                parentLayout?.error = null
                // Restore the password toggle when valid.
                parentLayout?.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                isValid = true
            }
        }
    }
}