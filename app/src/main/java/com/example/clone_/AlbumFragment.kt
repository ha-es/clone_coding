package com.example.clone_

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.clone_.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private var gson : Gson = Gson()

    private val infomation = arrayListOf("수록곡", "상세정보","영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        setInit(album)

//        setFragmentResultListener("TitleInfo") { requestKey, bundle ->
//            binding.albumMusicTitleTv.text = bundle.getString("title")
//        }
//        setFragmentResultListener("SingerInfo") { requestKey, bundle ->
//            binding.albumSingerNameTv.text = bundle.getString("singer")
//        }

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        val albumAapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = infomation[position]
        }.attach()

        return binding.root
    }


    private fun setInit(album: Album){
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
    }
}