package com.example.clone_

import androidx.room.*

@Dao
interface AlbumDao {
    @Insert
    fun insert(album: Album)

    @Update
    fun update(album: Album)

    @Delete
    fun delete(album: Album)

    @Query("SELECT * FROM AlbumTable") // 테이블의 모든 값을 가져와라
    fun getAlbums(): List<Album>

    @Query("SELECT * FROM AlbumTable WHERE id = :id")
    fun getAlbum(id: Int): Album


//사용자 별 좋아요
    @Insert
    fun likeAlbum(like : Like)

    @Query("select id from LikeTable where userId =:userId and albumId = :albumId")
    fun isLikedAlbum(userId : Int, albumId : Int) : Int?

    @Query("delete from LikeTable where userId =:userId and albumId = :albumId")
    fun dislikedAlbum(userId : Int, albumId : Int)

    @Query("select at.* from LikeTable as lt left join AlbumTable as at on lt.albumId = at.id where lt.userId = :userId")
    fun getLikedAlbums(userId : Int) : List<Album>
}