package com.project.dicodingplayground.practice_modul.soundpool

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityMain7Binding
import okio.IOException

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMain7Binding? = null
    private val binding get() = _binding!!
    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false
    private var mMediaPlayer: MediaPlayer? = null
    private var isReady: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMain7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()

        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                spLoaded = true
            } else {
                Toast.makeText(this, "Gagal Load", Toast.LENGTH_SHORT).show()
            }
        }

        soundId = sp.load(this, R.raw.ic_clicking_glasses, 1)


        binding.btnSoundPool.setOnClickListener {
            if (spLoaded) {
                sp.play(soundId, 1f, 1f, 0, 0, 1f)
            }
        }

        init()
        binding.btnPlay.setOnClickListener {
            if (!isReady) {
                mMediaPlayer?.prepareAsync()
            } else {
                if (mMediaPlayer?.isPlaying as Boolean) {
                    mMediaPlayer?.pause()
                } else {
                    mMediaPlayer?.start()
                }
            }
        }

        binding.btnStop.setOnClickListener {
            if (mMediaPlayer?.isPlaying as Boolean || isReady) {
                mMediaPlayer?.stop()
                isReady = false
            }
        }
    }

    private fun init() {
        mMediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mMediaPlayer?.setAudioAttributes(attribute)
        val afd = applicationContext.resources.openRawResourceFd(R.raw.ic_clicking_glasses)
        try {
            mMediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mMediaPlayer?.setOnPreparedListener {
            isReady = true
            mMediaPlayer?.start()
        }
        mMediaPlayer?.setOnErrorListener { _, _, _ -> false}
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}