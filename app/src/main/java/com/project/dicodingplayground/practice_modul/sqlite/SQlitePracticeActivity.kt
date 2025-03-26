package com.project.dicodingplayground.practice_modul.sqlite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.project.dicodingplayground.databinding.ActivitySqlitePracticeBinding
import com.project.dicodingplayground.practice_modul.sqlite.db.NoteHelper
import com.project.dicodingplayground.practice_modul.sqlite.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SQlitePracticeActivity : AppCompatActivity() {

    private var _binding : ActivitySqlitePracticeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NoteAdapter

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            when (result.resultCode) {
                NoteAddEditActivity.RESULT_ADD -> {
                    val note = result.data?.getParcelableExtra<Note>(NoteAddEditActivity.EXTRA_NOTE) as Note
                    adapter.addItem(note)
                    binding.rvNotes.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                NoteAddEditActivity.RESULT_UPDATE -> {
                    val note = result.data?.getParcelableExtra<Note>(NoteAddEditActivity.EXTRA_NOTE) as Note
                    val position = result.data?.getIntExtra(NoteAddEditActivity.EXTRA_POSITION, 0) as Int
                    adapter.updateItem(position, note)
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
                NoteAddEditActivity.RESULT_DELETE -> {
                    val position = result.data?.getIntExtra(NoteAddEditActivity.EXTRA_POSITION, 0) as Int
                    adapter.removeItem(position)
                    showSnackbarMessage("Satu item berhasil dihapus")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySqlitePracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvNotes.layoutManager = LinearLayoutManager(this@SQlitePracticeActivity, LinearLayoutManager.VERTICAL, false)
            rvNotes.setHasFixedSize(true)
            adapter = NoteAdapter(object : NoteAdapter.OnItemClickCallback{
                override fun onItemClicked(selectedNote: Note?, position: Int?) {
                    val intent = Intent(this@SQlitePracticeActivity, NoteAddEditActivity::class.java)
                    intent.putExtra(NoteAddEditActivity.EXTRA_NOTE, selectedNote)
                    intent.putExtra(NoteAddEditActivity.EXTRA_POSITION, position)
                    resultLauncher.launch(intent)
                }
            })
            rvNotes.adapter = adapter

            fabAdd.setOnClickListener {
                val intent = Intent(this@SQlitePracticeActivity, NoteAddEditActivity::class.java)
                resultLauncher.launch(intent)
            }
        }


        if (savedInstanceState == null) {
            // proses ambil data
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Note>(EXTRA_STATE)
            if (list != null) {
                adapter.listNotes = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    private fun loadNotesAsync() {
        lifecycleScope.launch {
            binding.apply {
                progressbar.visibility = View.VISIBLE
                val noteHelper = NoteHelper.getInstance(this@SQlitePracticeActivity)
                noteHelper.open()
                val defferedNotes = async(Dispatchers.IO) {
                    val cursor = noteHelper.queryAll()
                    MappingHelper.mapCursorToArrayList(cursor)
                }
                progressbar.visibility = View.INVISIBLE
                val notes = defferedNotes.await()
                if (notes.isNotEmpty()) {
                    adapter.listNotes = notes
                } else {
                    adapter.listNotes = ArrayList()
                    showSnackbarMessage("Tidak ada data tersedia!")
                }
                noteHelper.close()
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_STATE = "state"
    }

}