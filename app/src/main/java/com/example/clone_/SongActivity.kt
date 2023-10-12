package com.example.clone_

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.clone_.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var title : String? = null
        var singer : String? = null


        // 다운버튼을 눌렀을 때 다시 메인 액티비티로
        binding.songDownIb.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", title + " _ " + singer)

            setResult(RESULT_OK, intent)
            finish()
        }

        // 재생버튼을 눌렀을 경우 정지버튼이 보이도록
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }


        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        if(intent.hasExtra("title")&& intent.hasExtra("singer")){
            title = intent.getStringExtra("title")
            singer = intent.getStringExtra("singer")
            binding.songMusicTitleTv.text = title
            binding.songSingerNameTv.text = singer
        }
    }


    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){  //재생중
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }else {         //일시정지
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }
}