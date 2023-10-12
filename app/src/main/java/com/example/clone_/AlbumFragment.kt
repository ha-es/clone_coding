package com.example.clone_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.clone_.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding
    private val info = arrayListOf("수록곡","상세정보","영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val title = arguments?.getString("title")
        val singer = arguments?.getString("singer")

//        if (title=="LILAC"&&singer=="아이유 (IU)"){
//            binding.albumMusicTitleTv.text= "IU 5th Album 'LILAC'"
//            binding.albumSingerNameTv.text= singer
//            binding.albumAlbumIv.setImageResource(R.drawable.img_album_exp2)
//        }

        binding.albumAlbumIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }
}