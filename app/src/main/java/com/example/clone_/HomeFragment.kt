package com.example.clone_

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.widget.ViewPager2
import com.example.clone_.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeAlbumImgIv1.setOnClickListener {
            setFragmentResult("TitleInfo", bundleOf("title" to binding.titleLilac.text.toString()))
            setFragmentResult("SingerInfo", bundleOf("singer" to binding.singerIu.text.toString()))

            (context as MainActivity)
                .supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }


        val bannerAdapter = BannerVPAdapter(this)
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        //ViewPager2 적용
        val pannelAdpater = PannelVpAdapter(this)
        pannelAdpater.addFragment(PannelFragment(R.drawable.img_first_album_default))
        pannelAdpater.addFragment(PannelFragment(R.drawable.img_first_album_default))
        binding.homePannelBackgroundVp.adapter = pannelAdpater
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundVp)

        autoSlide((pannelAdpater))

        return binding.root
    }


    //자동으로 넘어가기
    private fun autoSlide(adapter: PannelVpAdapter) {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    val nextItem = binding.homePannelBackgroundVp.currentItem + 1
                    if (nextItem < adapter.itemCount) {
                        binding.homePannelBackgroundVp.currentItem = nextItem
                    } else {
                        binding.homePannelBackgroundVp.currentItem = 0 // 순환
                    }
                }
            }
        }, 3000, 3000)  //3초
    }
}