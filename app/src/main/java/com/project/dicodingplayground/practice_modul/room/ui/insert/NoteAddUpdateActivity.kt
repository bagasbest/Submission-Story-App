package com.project.dicodingplayground.practice_modul.room.ui.insert

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityNoteAddUpdateBinding
import com.project.dicodingplayground.practice_modul.room.database.Note
import com.project.dicodingplayground.practice_modul.room.helper.DateHelper
import com.project.dicodingplayground.practice_modul.room.helper.ViewModelFactory

class NoteAddUpdateActivity : AppCompatActivity() {

    private var _binding: ActivityNoteAddUpdateBinding? = null
    private val binding get() = _binding!!
    private var isEdit = false
    private var note: Note? = null
    private lateinit var viewModel: NoteAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(this@NoteAddUpdateActivity)
        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }
        binding.apply {
            if (isEdit) {
                if (note != null) {
                    note?.let { note ->
                        edtTitle.setText(note.title)
                        edtDescription.setText(note.description)
                    }
                }
            }
            btnSubmit.setOnClickListener {
                val title = edtTitle.text.toString().trim()
                val description = edtDescription.text.toString().trim()
                when {
                    title.isEmpty() -> {
                        edtTitle.error = getString(R.string.empty)
                    }
                    description.isEmpty() -> {
                        edtDescription.error = getString(R.string.empty)
                    }
                    else -> {
                        note.let { note ->
                            note?.title = title
                            note?.description = description
                        }
                        if (isEdit) {
                            viewModel.update(note as Note)
                        } else {
                            note.let { note ->
                                note?.date = DateHelper.getCurrentDate()
                            }
                            viewModel.insert(note as Note)
                        }
                        finish()
                    }
                }
            }
            btnDelete.setOnClickListener {
                showAlertDialog(ALERT_DIALOG_DELETE)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[NoteAddUpdateViewModel::class.java]
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    viewModel.delete(note as Note)
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showAlertDialog(ALERT_DIALOG_CLOSE)
            return true // Indicate that the event is handled
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}