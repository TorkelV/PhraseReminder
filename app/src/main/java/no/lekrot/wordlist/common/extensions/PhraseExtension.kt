package no.lekrot.wordlist.common.extensions

import no.lekrot.wordlist.phrases.data.PhraseDTO
import no.lekrot.wordlist.phrases.data.RPhraseDTO
import no.lekrot.wordlist.phrases.domain.Phrase

fun Phrase.toPhraseDTO(): PhraseDTO = PhraseDTO(this.id, this.phrase, this.translation, this.group)
fun Phrase.toRPhraseDTO(refId: String): RPhraseDTO = RPhraseDTO(this.id, refId, this.phrase, this.translation, this.group)
fun RPhraseDTO.toPhrase(): Phrase = Phrase(this.id, this.phrase, this.translation, this.group)
fun PhraseDTO.toPhrase(): Phrase = Phrase(this.id, this.phrase, this.translation, this.group)
