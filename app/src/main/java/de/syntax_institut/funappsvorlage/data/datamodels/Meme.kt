package de.syntax_institut.funappsvorlage.data.datamodels

/**
 * Diese Klasse steht für ein Meme
 * @param name der Titel des Memes
 * @param url die URL des Bildes des Memes
 */
data class Meme(
    var name: String,
    val url: String
)
