package de.syntax_institut.funappsvorlage.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax_institut.funappsvorlage.data.datamodels.MemeData
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Die Konstante enthält die URL der API
const val BASE_URL = "https://api.imgflip.com/"

// Moshi konvertiert Serverantworten in Kotlin Objekte
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit übernimmt die Kommunikation und übersetzt die Antwort
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Das Interface bestimmt, wie mit dem Server kommuniziert wird
interface MemeApiService {

    /**
     * Diese Funktion spezifiziert die URL uns holt die Liste an Informationen
     */
    @GET("get_memes")
    suspend fun getMemes(): MemeData
}

// Das Objekt dient als Zugangspunkt für den Rest der App und stellt den API Service zur Verfügung
object MemeApi {
    val retrofitService: MemeApiService by lazy { retrofit.create(MemeApiService::class.java) }
}
