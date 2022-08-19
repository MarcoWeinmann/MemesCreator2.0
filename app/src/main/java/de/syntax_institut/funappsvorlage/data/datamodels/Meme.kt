package de.syntax_institut.funappsvorlage.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Diese Klasse steht für ein Meme
 * @param name der Titel des Memes
 * @param url die URL des Bildes des Memes
 */
@Entity
data class Meme(
    @PrimaryKey
    @Json(name = "name")
    var name: String,

    @Json(name = "url")
    val url: String,

    @Json(name = "id")
    var id: Long
)
