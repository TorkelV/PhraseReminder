package no.lekrot.wordlist.main

import no.lekrot.wordlist.main.view.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
}
