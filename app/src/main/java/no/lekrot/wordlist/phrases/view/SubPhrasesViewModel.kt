package no.lekrot.wordlist.phrases.view

import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import no.lekrot.wordlist.BR
import no.lekrot.wordlist.R
import no.lekrot.wordlist.common.extensions.toPhrase
import no.lekrot.wordlist.common.extensions.toRPhraseDTO
import no.lekrot.wordlist.phrases.data.PhraseDTO
import no.lekrot.wordlist.phrases.data.PhraseDao
import no.lekrot.wordlist.phrases.data.RPhraseDTO
import no.lekrot.wordlist.phrases.domain.AddPhraseComponent
import no.lekrot.wordlist.phrases.domain.Phrase

class SubPhrasesViewModel(
    private val args: SubPhrasesFragmentArgs,
    private val db: PhraseDao
) : ViewModel() {

    private val phraseFlow = db.get(args.phraseId).map(PhraseDTO::toPhrase)
    val groupId = phraseFlow.map { it.group }.asLiveData(viewModelScope.coroutineContext)
    val displayedPhrases: LiveData<List<Phrase>> =
        db.getAllRelated(args.phraseId).combine(phraseFlow) { w1, w2 ->
            listOf(w2) + (w1).map(RPhraseDTO::toPhrase)
        }.asLiveData(viewModelScope.coroutineContext)

    val addPhraseComponent = AddPhraseComponent { phrase ->
        viewModelScope.launch(Dispatchers.IO) {
            db.insertAll(phrase.toRPhraseDTO(phraseFlow.first().id))
        }
    }

    val canDelete = MutableLiveData(false)
    val itemBinding = ItemBinding.of<Phrase> { itemBinding, position, item ->
        itemBinding.set(BR.phrase, R.layout.component_word).bindExtra(
            BR.onDelete,
            View.OnClickListener {
                viewModelScope.launch(Dispatchers.IO) {
                    db.deleteRelated(item.toRPhraseDTO(args.phraseId))
                }
            }
        ).bindExtra(
            BR.onLongClick,
            View.OnLongClickListener {
                canDelete.value = true
                true
            }
        ).bindExtra(
            BR.canDelete,
            if (item.id == args.phraseId) MutableLiveData(false) else canDelete
        ).bindExtra(
            BR.onPhraseClick,
            View.OnClickListener {}
        ).bindExtra(
            BR.i,
            position
        )
    }
}
