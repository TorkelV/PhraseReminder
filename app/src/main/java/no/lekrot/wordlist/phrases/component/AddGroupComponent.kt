package no.lekrot.wordlist.phrases.component

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import no.lekrot.wordlist.common.databinding.Component

class AddGroupComponent() : Component {
    val text = MutableLiveData<String>()
    val enabled = text.map { !it.isNullOrBlank() }
}
