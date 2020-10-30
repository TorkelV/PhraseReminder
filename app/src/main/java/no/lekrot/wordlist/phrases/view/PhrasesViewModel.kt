package no.lekrot.wordlist.phrases.view

import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import no.lekrot.wordlist.BR
import no.lekrot.wordlist.R
import no.lekrot.wordlist.common.extensions.toPhrase
import no.lekrot.wordlist.common.extensions.toPhraseDTO
import no.lekrot.wordlist.common.livedata.Event
import no.lekrot.wordlist.common.service.PreferenceService
import no.lekrot.wordlist.phrases.component.PhraseSettings
import no.lekrot.wordlist.phrases.component.SearchComponent
import no.lekrot.wordlist.phrases.data.PhraseDTO
import no.lekrot.wordlist.phrases.data.PhraseDao
import no.lekrot.wordlist.phrases.domain.AddPhraseComponent
import no.lekrot.wordlist.phrases.domain.Phrase
import se.ingenuity.lives.Lives

class PhrasesViewModel(private val db: PhraseDao, prefs: PreferenceService) : ViewModel() {

    private val _navigation = MutableLiveData<PhrasesNavigationEvent>()
    val navigation = _navigation.map(::Event)

    val searchComponent = SearchComponent()
    val phraseSettings = PhraseSettings(
        onDeletePhrase = { phrase ->
            viewModelScope.launch(Dispatchers.IO) {
                db.delete(phrase.toPhraseDTO())
            }
        }
    )

    val addPhraseComponent = AddPhraseComponent { phrase ->
        viewModelScope.launch(Dispatchers.IO) {
            db.insertAll(phrase.toPhraseDTO())
        }
    }

    val groupId: LiveData<String> = prefs.activeGroup.asLiveData(viewModelScope.coroutineContext)

    private val phrases: LiveData<List<Phrase>> = groupId.switchMap { group ->
        db.getAll(group).map { w -> w.map(PhraseDTO::toPhrase) }
            .asLiveData(viewModelScope.coroutineContext)
    }

    val displayedPhrases =
        Lives.combineLatest(
            phrases,
            searchComponent.searchString,
            searchComponent.visible
        ) { phrases, search, enabled ->
            if (enabled) {
                phrases.reversed().filter {
                    it.phrase.contains(search, true) ||
                        it.translation.contains(search, true)
                }
            } else {
                phrases.reversed()
            }
        }

    val itemBinding = ItemBinding.of<Phrase> { itemBinding, position, item ->
        itemBinding.set(BR.phrase, R.layout.component_word)
            .bindExtra(
                BR.phraseSettings,
                phraseSettings
            )
            .bindExtra(
                BR.onLongClick,
                View.OnLongClickListener {
                    phraseSettings.show()
                    true
                }
            ).bindExtra(
                BR.onPhraseClick,
                View.OnClickListener {
                    _navigation.value = PhrasesNavigationToPhrase(item.id)
                }
            ).bindExtra(
                BR.i,
                position
            )
    }

    val canLeave: LiveData<Boolean> =
        Lives.combineLatest(
            searchComponent.visible,
            addPhraseComponent.visible,
            phraseSettings.visible
        ) { a, b, c ->
            !a && !b && !c
        }.distinctUntilChanged()

    fun prepareToLeave() {
        addPhraseComponent.close()
        searchComponent.close()
        phraseSettings.close()
    }
}
