package com.example.clone_

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAdapter (fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> SavedSongFragment()
            1 -> MusicfileFragment()
            else -> SavedAlbumFragment()
        }
    }

}