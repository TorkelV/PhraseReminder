package no.lekrot.wordlist.phrases.view

sealed class PhrasesNavigationEvent
data class PhrasesNavigationToPhrase(val phraseId: String) : PhrasesNavigationEvent()
