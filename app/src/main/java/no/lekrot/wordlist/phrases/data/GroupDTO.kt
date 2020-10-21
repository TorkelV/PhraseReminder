package no.lekrot.wordlist.phrases.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class GroupDTO(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name", defaultValue = "default") val name: String = "default"
)
