package de.syntax_institut.funappsvorlage.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.syntax_institut.funappsvorlage.data.datamodels.Meme
import de.syntax_institut.funappsvorlage.data.local.MemeDatabase
import de.syntax_institut.funappsvorlage.data.remote.MemeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Exception

const val TAG = "AppRepositoryTAG"

/**
 * Diese Klasse holt die Informationen und stellt sie mithilfe von Live Data dem Rest
 * der App zur Verfügung
 */
class AppRepository(private val api: MemeApi, private val database: MemeDatabase) {

    // Die LiveData Variable memes enthält die Liste aus dem API call
    private val _memes = database.memeDatabaseDao.getAll()
    val memes: LiveData<List<Meme>>
        get() = _memes

    /**
     * Diese Funktion ruft die Daten aus dem API Service ab und speichert die Antwort in der
     * Variable memes. Falls der Call nicht funktioniert, wird die Fehlermeldung geloggt
     */
    suspend fun getMemes() {
//        Log.e(TAG, "get Memes")
//        try {
//            val memeData = api.retrofitService.getMemes()
//            _memes.value = memeData.data.memes.shuffled()
//        } catch (e: Exception) {
//            Log.e(TAG, "Error loading Data from API: $e")
//        }
        withContext(Dispatchers.IO) {
            val newMemeList = api.retrofitService.getMemes().data.memes
            database.memeDatabaseDao.insertAll(newMemeList)
        }
    }

    suspend fun deleteMeme(meme: Meme){
        try {
            database.memeDatabaseDao.deleteById(meme.id)
        }catch (e: Exception){
            Log.d(TAG, "Failed to delete from Database")
        }
    }

    suspend fun deleteAllMemes(meme: Meme){
        try {
            database.memeDatabaseDao.deleteAll()
        }catch (e: Exception){
            Log.d(TAG, "Failed to delete all from Dtabase")
        }
    }

    suspend fun updateMemes(meme: Meme){
        try {
            database.memeDatabaseDao.update(meme)
        }catch (e: Exception){
            Log.d(TAG, "Failed to update Database: $e")
        }
    }
}
