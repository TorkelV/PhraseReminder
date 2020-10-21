package no.lekrot.wordlist.phrases.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "relatedPhrases", primaryKeys = ["id", "ref"])
data class RPhraseDTO(
    val id: String,
    val ref: String,
    @ColumnInfo(name = "phrase") val phrase: String,
    @ColumnInfo(name = "translation") val translation: String,
    val group: String
)
