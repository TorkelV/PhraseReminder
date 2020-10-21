package no.lekrot.wordlist.startup.view

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import no.lekrot.wordlist.BR
import no.lekrot.wordlist.R
import no.lekrot.wordlist.common.livedata.Event
import no.lekrot.wordlist.common.service.PreferenceService
import se.ingenuity.lives.Lives

class OnboardingViewModel(
    context: Context,
    private val prefs: PreferenceService
) : ViewModel() {

    private val _navigationEvent = MutableLiveData<OnboardingNavigationEvent>()
    val navigationEvent = _navigationEvent.map(::Event)

    val currentPage = MutableLiveData<Int>()

    val pages = MutableLiveData(
        listOf(
            OnboardingPage(
                context.getString(R.string.onboarding_1_title),
                context.getString(R.string.onboarding_1_text),
                ContextCompat.getDrawable(context, R.drawable.add_preview),
                context.getString(R.string.onboarding_button)
            ),
            OnboardingPage(
                context.getString(R.string.onboarding_2_title),
                context.getString(R.string.onboarding_2_text),
                ContextCompat.getDrawable(context, R.drawable.search_preview),
                context.getString(R.string.onboarding_button)
            ),
            OnboardingPage(
                context.getString(R.string.onboarding_3_title),
                context.getString(R.string.onboarding_3_text),
                ContextCompat.getDrawable(context, R.drawable.related_preview),
                context.getString(R.string.onboarding_button_last)
            )
        )
    )

    val buttonText: LiveData<CharSequence> =
        Lives.combineLatest(currentPage, pages) { currentPage, pages ->
            pages[currentPage].buttonText
        }

    val itemBinding: ItemBinding<OnboardingPage> =
        ItemBinding.of(BR.onboardingPage, R.layout.onboarding_page)

    fun nextPage(current: Int, pages: List<OnboardingPage>) {
        if (current < pages.lastIndex) {
            currentPage.value = current.inc()
        } else {
            completeOnboarding()
        }
    }

    private fun completeOnboarding() {
        GlobalScope.launch {
            prefs.completeOnboarding()
        }
        _navigationEvent.value = OnboardingFinish
    }
}
