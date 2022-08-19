package de.syntax_institut.funappsvorlage.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import de.syntax_institut.funappsvorlage.data.datamodels.Meme

@Dao
 interface MemeDatabaseDao {

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertAll(meme: List<Meme>)

    @Update
    suspend fun update(meme: Meme)

    @Query("SELECT * FROM Meme")
    fun getAll(): LiveData<List<Meme>>

    @Query("DELETE FROM Meme")
    suspend fun deleteAll()

    @Query("DELETE FROM Meme WHERE id = :id")
    suspend fun deleteById(id: Long)
}