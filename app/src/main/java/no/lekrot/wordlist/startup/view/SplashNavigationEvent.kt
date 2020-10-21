package no.lekrot.wordlist.startup.view

sealed class SplashNavigationEvent()

object SplashNavigationOnboarding : SplashNavigationEvent()

object SplashNavigationMain : SplashNavigationEvent()
