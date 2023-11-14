package com.example.clone_

import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.clone_.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        // data list 생성 더미 데이터
        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)",R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)",R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)",R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)",R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)",R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)",R.drawable.img_album_exp6))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)


        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            private fun changeAlbumFragment(album: Album) {
                (context as MainActivity)
                    .supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AlbumFragment().apply {
                        arguments = Bundle().apply {
                            val gson = Gson()
                            val albumJson = gson.toJson(album)
                            putString("album", albumJson)
                        }
                    }).commitAllowingStateLoss()
            }

//            override fun onRemoveAlbum(position: Int) {
//
//            }
        })

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