package no.lekrot.wordlist.phrases.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PhraseDTO::class, RPhraseDTO::class, GroupDTO::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun phraseDao(): PhraseDao
}
