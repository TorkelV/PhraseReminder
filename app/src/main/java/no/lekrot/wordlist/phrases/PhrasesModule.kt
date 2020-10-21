package no.lekrot.wordlist.phrases

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import no.lekrot.wordlist.phrases.data.AppDatabase
import no.lekrot.wordlist.phrases.data.PhraseDao
import no.lekrot.wordlist.phrases.view.PhrasesViewModel
import no.lekrot.wordlist.phrases.view.SubPhrasesFragmentArgs
import no.lekrot.wordlist.phrases.view.SubPhrasesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val phraseModule = module {
    viewModel { (args: SubPhrasesFragmentArgs) -> SubPhrasesViewModel(args, get()) }
    viewModel { PhrasesViewModel(get(), get()) }
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, "database-name"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("INSERT INTO groups (id, name) VALUES ('1', 'default')")
            }
        }).build()
    }
    fun providePhraseDao(db: AppDatabase): PhraseDao = db.phraseDao()
    single<PhraseDao> { providePhraseDao(get()) }
}
