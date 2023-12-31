package com.example.clone_

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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

class HomeFragment : Fragment(), SendInterface  {
    lateinit var binding: FragmentHomeBinding

    private lateinit var songDB: SongDatabase
    private var albumDatas = ArrayList<Album>()

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())


    //miniplayer
    override fun sendData(album: Album) {
        if(activity is MainActivity){
            val activity = activity as MainActivity
            activity.updateMainPlayerCl(album)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums()) // songDB에서 album list를 가져옵니다.
        Log.d("albumlist", albumDatas.toString())

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)


        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {

            override fun onItemClick(album: Album)  {
                (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment().apply {
                    arguments = Bundle().apply {
                        val gson = Gson()
                        val albumJson = gson.toJson(album)
                        putString("album", albumJson)
                    }
                }).commitAllowingStateLoss()
            }

            override fun onPlayAlbum(album: Album) {
                sendData(album)
            }

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

    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val songs = songDB.albumDao().getAlbums()

        if (songs.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                1,
                "LILAC",
                "아이유 (IU)",
                R.drawable.img_album_exp2
            )
        )

        songDB.albumDao().insert(
            Album(
                2,
                "Butter",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp
            )
        )

        songDB.albumDao().insert(
            Album(
                3,
                "Next Level",
                "에스파 (AESPA)",
                R.drawable.img_album_exp3
            )
        )

        songDB.albumDao().insert(
            Album(
                4,
                "Boy with Luv",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp4,
            )
        )


        songDB.albumDao().insert(
            Album(
                5,
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                R.drawable.img_album_exp5
            )
        )

        val songDBData = songDB.albumDao().getAlbums()
        Log.d("DB data", songDBData.toString())
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