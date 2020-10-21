package no.lekrot.wordlist.phrases.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PhraseDao {

    @Query("SELECT * FROM phrases")
    fun getAll(): List<PhraseDTO>

    @Query("SELECT * FROM phrases WHERE `group` = :groupId")
    fun getAll(groupId: String): Flow<List<PhraseDTO>>

    @Query("SELECT * from phrases where id = :id")
    fun get(id: String): Flow<PhraseDTO>

    @Insert
    fun insertAll(vararg phrases: PhraseDTO)

    @Delete
    fun delete(phrase: PhraseDTO)

    @Query("SELECT * FROM relatedPhrases where ref = :id")
    fun getAllRelated(id: String): Flow<List<RPhraseDTO>>

    @Insert
    fun insertAll(vararg phrases: RPhraseDTO)

    @Delete
    fun delete(phrase: RPhraseDTO)

    @Query("SELECT * FROM groups")
    fun getAllGroups(): Flow<List<GroupDTO>>

    @Delete
    fun delete(word: GroupDTO)

    @Insert
    fun insertAll(vararg groups: GroupDTO)
}
