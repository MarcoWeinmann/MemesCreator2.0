package de.syntax_institut.funappsvorlage.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.syntax_institut.funappsvorlage.data.datamodels.Meme

@Database(entities = [Meme::class], version = 1)
abstract class MemeDatabase: RoomDatabase() {

    abstract val memeDatabaseDao: MemeDatabaseDao





}
private lateinit var INSTANCE: MemeDatabase

fun getDatabase(context: Context): MemeDatabase{
    synchronized(MemeDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MemeDatabase::class.java,
                "guest_deluxe_database"
            )
                .build()
        }
    }
    return INSTANCE
}

