package no.lekrot.wordlist

import android.app.Application
import no.lekrot.wordlist.common.commonModule
import no.lekrot.wordlist.main.mainModule
import no.lekrot.wordlist.phrases.phraseModule
import no.lekrot.wordlist.startup.onboardingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(mainModule, commonModule, onboardingModule, phraseModule))
        }
    }
}
