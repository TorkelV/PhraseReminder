package no.lekrot.wordlist.phrases.domain

import no.lekrot.wordlist.common.databinding.Component

data class Phrase(val id: String, val phrase: String, val translation: String, val group: String) : Component
