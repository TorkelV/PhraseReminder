package no.lekrot.wordlist.common

import no.lekrot.wordlist.common.service.PreferenceService
import org.koin.dsl.module

val commonModule = module {
    single { PreferenceService(get()) }
}
