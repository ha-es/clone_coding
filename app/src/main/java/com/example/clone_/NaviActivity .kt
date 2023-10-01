package com.example.clone_

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.clone_.databinding.ActivityNaviBinding


private const val TAG_HOME = "home_fragment"


class NaviActivity  : AppCompatActivity() {

    //뷰바인딩 사용
    private lateinit var binding : ActivityNaviBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //맨 처음 보여줄 프래그먼트
        setFragment(TAG_HOME, HomeFragment())


        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> setFragment(TAG_HOME, HomeFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val home = manager.findFragmentByTag(TAG_HOME)

        if (home != null){
            fragTransaction.hide(home)
        }

        else if (tag == TAG_HOME) {
            if (home != null) {
                fragTransaction.show(home)
            }
        }

        fragTransaction.commitAllowingStateLoss()

    }
}