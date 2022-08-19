package de.syntax_institut.funappsvorlage.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import de.syntax_institut.funappsvorlage.data.AppRepository
import de.syntax_institut.funappsvorlage.data.TAG
import de.syntax_institut.funappsvorlage.data.datamodels.Meme
import de.syntax_institut.funappsvorlage.data.local.getDatabase
import de.syntax_institut.funappsvorlage.data.remote.MemeApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

enum class ApiStatus {LOADING, ERROR, DONE}

class MemesViewModel(application: Application): AndroidViewModel(application) {

    // hier wird eine AppRepository Instanz erstellt, mit dem Parameter MemeApi
    private val database = getDatabase(application)

    private val repository = AppRepository(MemeApi, database)

    // hier werden die memes aus dem repository in einer eigenen Variablen gespeichert
    val memes = repository.memes

    /**
     * Diese Funktion ruft die Repository-Funktion zum Laden der Memes
     * innerhalb einer Coroutine auf
     */
    private val _loading = MutableLiveData<ApiStatus>()
    val loading: LiveData<ApiStatus>
        get() = _loading

    fun loadData() {
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            try {
                repository.getMemes()
                _loading.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.e(TAG, "Error loading Data $e")
                if (memes.value.isNullOrEmpty()) {
                    _loading.value = ApiStatus.ERROR
                } else {
                    _loading.value = ApiStatus.DONE
                }
            }
        }
    }

    fun updateMeme(meme: Meme){
        viewModelScope.launch {
            repository.updateMemes(meme)
            loading.value
        }
    }

    fun deleteMeme(meme: Meme){
        viewModelScope.launch {
            repository.deleteMeme(meme)
            loading.value
        }
    }

}
