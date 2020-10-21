package no.lekrot.wordlist.startup.view

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import no.lekrot.wordlist.common.livedata.Event
import no.lekrot.wordlist.common.service.PreferenceService

class SplashViewModel(
    preferences: PreferenceService
) : ViewModel() {

    private val _navigationEvent = MutableLiveData<SplashNavigationEvent>()
    val navigationEvent = _navigationEvent.map(::Event)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when {
                preferences.isAppOnboarded.first().not() -> _navigationEvent.postValue(
                    SplashNavigationOnboarding
                )
                else -> _navigationEvent.postValue(SplashNavigationMain)
            }
        }
    }
}
