package com.example.clone_

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clone_.databinding.FragmentAlbumBinding
import com.example.clone_.databinding.FragmentLockerBinding


class LockerFragment : Fragment() {


    lateinit var binding: FragmentLockerBinding
    private val infomation = arrayListOf("저장한 곡", "음악파일")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container,false)

        return  binding.root
    }

}