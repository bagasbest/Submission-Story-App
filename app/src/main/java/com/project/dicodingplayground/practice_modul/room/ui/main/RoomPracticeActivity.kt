package com.project.dicodingplayground.practice_modul.room.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.dicodingplayground.databinding.ActivityRoomPracticeBinding
import com.project.dicodingplayground.practice_modul.room.helper.ViewModelFactory
import com.project.dicodingplayground.practice_modul.room.ui.insert.NoteAddUpdateActivity

class RoomPracticeActivity : AppCompatActivity() {

    private var _binding: ActivityRoomPracticeBinding? = null
    private val binding get() = _binding!!
    private var adapter = RoomPracticeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRoomPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            rvNotes.layoutManager = LinearLayoutManager(this@RoomPracticeActivity, LinearLayoutManager.VERTICAL, false)
            rvNotes.setHasFixedSize(true)
            rvNotes.adapter = adapter
            val viewModel = obtainViewModel(this@RoomPracticeActivity)
            viewModel.getAllNotes().observe(this@RoomPracticeActivity) {
                if (it != null) {
                    adapter.setListNotes(it)
                }
            }

            fabAdd.setOnClickListener {
                val intent = Intent(this@RoomPracticeActivity, NoteAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }

    }


    private fun obtainViewModel(activity: AppCompatActivity): RoomPracticeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[RoomPracticeViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}