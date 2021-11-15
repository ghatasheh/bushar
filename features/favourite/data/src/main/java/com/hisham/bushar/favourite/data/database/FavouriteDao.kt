package com.hisham.bushar.favourite.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hisham.bushar.favourite.data.database.entites.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourite")
    fun observeAll(): Flow<List<FavouriteEntity>>

    @Query("SELECT id FROM favourite")
    suspend fun getIds(): List<Int>

    @Query("SELECT * FROM favourite")
    fun getAllByPage(): PagingSource<Int, FavouriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg entities: FavouriteEntity)

    @Delete
    suspend fun delete(entity: FavouriteEntity)
}
