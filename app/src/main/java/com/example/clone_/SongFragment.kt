package com.example.clone_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.clone_.databinding.FragmentDetailBinding
import com.example.clone_.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)


        binding.songMixoffTg.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songMixonTg.setOnClickListener {
            setPlayerStatus(false)
        }


        return binding.root



    }



    fun setPlayerStatus (isPlaying : Boolean){
        if(isPlaying){
            binding.songMixoffTg.visibility = View.GONE
            binding.songMixonTg.visibility = View.VISIBLE
        } else {
            binding.songMixoffTg.visibility = View.VISIBLE
            binding.songMixonTg.visibility = View.GONE
        }
    }
}