package com.example.clone_

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.clone_.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.util.*


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    private var song : Song = Song()
    private var gson : Gson = Gson()
    private var mediaPlayer: MediaPlayer? = null
    lateinit var timer: Timer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Clone_)
        binding = ActivityMainBinding.inflate(layoutInflater)   //초기화
        setContentView(binding.root)

        startTimer()
        inputDummySongs()



        binding.mainPlayerCl.setOnClickListener {
//            val intent = Intent(this, SongActivity::class.java)
//            intent.putExtra("title", song.title)
//            intent.putExtra("singer",song.singer)
//            intent.putExtra("second",song.second)
//            intent.putExtra("playTime",song.playTime)
//            intent.putExtra("isPlaying",song.isPlaying)
//            intent.putExtra("music",song.music)
//           // startActivity(intent)
//            getResultText.launch(intent)

            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this,SongActivity::class.java)
            startActivity(intent)
        }

        binding.mainMiniplayerBtn.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.mainPauseBtn.setOnClickListener {
            setPlayerStatus(false)
        }

        initBottomNavigation()
    }

    private fun initBottomNavigation(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item->
            when (item.itemId){

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }


    private fun setMiniPlayer(song:Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime
    }
    override fun onStart() {
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData",null)
//
//        song = if(songJson==null){
//            Song("라일락","아이유(IU)",0,60,false,"music_lilac")
//        }
//        else{
//            // SongActivity에서 노래가 한번이라도 pause 된 경우
//            gson.fromJson(songJson, Song::class.java)
//        }
//        setMiniPlayer(song)

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        // songId는 SongActivity에서 onPause가 호출되었을 때
        // 재생 중이던 노래의 id(pk)이다.
        val songId = spf.getInt("songId", 0)

        // SongDatabase의 인스턴스를 가져온다.
        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0){ // 재생 중이던 노래가 없었으면
            songDB.songDao().getSong(1)
        } else{ // 재생 중이던 노래가 있었으면
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song) // song의 정보로 MiniPlayer를 setting

    }


    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.mainMiniplayerProgressSb.progress * song.playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)
        editor.apply()

    }

    //miniplayer
    fun updateMainPlayerCl(album : Album) {
        binding.mainMiniplayerTitleTv.text = album.title
        binding.mainMiniplayerSingerTv.text = album.singer
        binding.mainMiniplayerProgressSb.progress = (song.second*1000/song.playTime)
        binding.mainMiniplayerBtn.visibility = View.GONE
        binding.mainPauseBtn.visibility = View.VISIBLE
        setPlayer(album.songs!!)
        setPlayerStatus(true)

    }

    fun setPlayerStatus(isPlaying: Boolean){

        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
            mediaPlayer?.start()
            startTimer()
        } else{
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()

            }
        }
    }


    private fun setPlayer(song: Song) {
        binding.mainMiniplayerProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        setPlayerStatus(song.isPlaying)

    }
    private  fun startTimer(){
        timer=Timer(song.playTime, song.isPlaying)
        timer.start()
    }


    inner class Timer(private  val playTime: Int, var isPlaying: Boolean = true) : Thread(){
        private  var second : Int =0
        private  var mills  :Float=0f

        override fun run(){
            super.run()

            try {
                while(true){

                    if(second >= playTime){
                        break
                    }
                    if (isPlaying){
                        sleep(50)
                        mills +=50

                        runOnUiThread {
                            binding.mainMiniplayerProgressSb.progress = ((mills / playTime)*100).toInt()
                        }

                    }

                }

            }catch (e: InterruptedException){
                Log.d("Song","스레드가 죽었습니다${e.message}" )
            }
        }
    }


    //roomDB
    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        // songs에 데이터가 이미 존재해 더미 데이터를 삽입할 필요가 없음
        if (songs.isNotEmpty()) return

        // songs에 데이터가 없을 때에는 더미 데이터를 삽입해주어야 함
        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_flu",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_next",
                R.drawable.img_album_exp3,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "music_boy",
                0,
                230,
                false,
                "music_boy",
                R.drawable.img_album_exp4,
                false,
            )
        )


        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
            )
        )

        // DB에 데이터가 잘 들어갔는지 확인
        val songDBData = songDB.songDao().getSongs()
        Log.d("DB data", songDBData.toString())
    }


}