package com.example.clone_

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clone_.databinding.FragmentAlbumBinding
import com.example.clone_.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment : Fragment() {


    lateinit var binding: FragmentLockerBinding
    private val infomation = arrayListOf("저장한곡", "음악파일")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container,false)

        val bottomSheetFragment = BottomSheetFragment()

        binding.lockerSelectAllTv.setOnClickListener {
            bottomSheetFragment.show(requireFragmentManager(), "BottomSheetDialog")
        }


        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp){
                tab, position ->
            tab.text = infomation[position]
        }.attach()


        binding.lockerLoginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return  binding.root
    }



}