package no.lekrot.wordlist.startup

import no.lekrot.wordlist.startup.view.OnboardingViewModel
import no.lekrot.wordlist.startup.view.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    viewModel { OnboardingViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
}
