package com.example.clone_

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clone_.databinding.ItemLockerAlbumBinding

class LockerAlbumRVAdapter (private val albumList: ArrayList<Album>) : RecyclerView.Adapter<LockerAlbumRVAdapter.ViewHolder>() {

    private val switchStatus = SparseBooleanArray()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LockerAlbumRVAdapter.ViewHolder {
        val binding: ItemLockerAlbumBinding = ItemLockerAlbumBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LockerAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.binding.itemLockerAlbumMoreIv.setOnClickListener {
            itemClickListener.onRemoveAlbum(position)
        }

        val switch = holder.binding.switchRV
        switch.isChecked = switchStatus[position]
        switch.setOnClickListener {
            if (switch.isChecked){
                switchStatus.put(position, true)
            }
            else {
                switchStatus.put(position, false)
            }
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemLockerAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemLockerAlbumTitleTv.text = album.title
            binding.itemLockerAlbumSingerTv.text = album.singer
            binding.itemLockerAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }


    interface OnItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    //추가
    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    //삭제제
   fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }



}