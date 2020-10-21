package no.lekrot.wordlist.phrases.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrases")
data class PhraseDTO(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "phrase") val phrase: String,
    @ColumnInfo(name = "translation") val translation: String,
    @ColumnInfo(name = "group", defaultValue = "1") val group: String = "1"
)
