package no.lekrot.wordlist.main.view

import android.content.Context
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.bindingcollectionadapter2.ItemBinding
import no.lekrot.wordlist.BR
import no.lekrot.wordlist.R
import no.lekrot.wordlist.common.databinding.Component
import no.lekrot.wordlist.common.livedata.Event
import no.lekrot.wordlist.common.service.PreferenceService
import no.lekrot.wordlist.main.MainNavigation
import no.lekrot.wordlist.main.MainNavigationBack
import no.lekrot.wordlist.phrases.component.AddGroupComponent
import no.lekrot.wordlist.phrases.component.ComponentDrawerGroup
import no.lekrot.wordlist.phrases.component.ComponentTitle
import no.lekrot.wordlist.phrases.data.GroupDTO
import no.lekrot.wordlist.phrases.data.PhraseDao
import no.lekrot.wordlist.phrases.domain.Group
import se.ingenuity.lives.Lives
import java.util.*

class MainViewModel(
    private val context: Context,
    private val preferenceService: PreferenceService,
    private val db: PhraseDao
) :
    ViewModel() {
    val currentFragment = MutableLiveData(R.id.splash_fragment)

    private val _navigation = MutableLiveData<MainNavigation>()
    val navigation = _navigation.map(::Event)

    val topNavVisible =
        currentFragment.map { id ->
            listOf(
                R.id.sub_phrases_fragment,
                R.id.phrases_fragment
            ).contains(id)
        }

    val toolbarTitle = currentFragment.map { id ->
        when (id) {
            R.id.phrases_fragment -> context.getString(R.string.app_name)
            else -> context.getString(R.string.back)
        }
    }

    val drawerEnabled = currentFragment.map { id ->
        when (id) {
            R.id.phrases_fragment -> true
            else -> false
        }
    }

    private val activeGroup =
        preferenceService.activeGroup.asLiveData(viewModelScope.coroutineContext)

    private val groups: LiveData<List<Group>> = db.getAllGroups().map { groups ->
        groups.map { g -> Group(g.id, g.name) }
    }.asLiveData(viewModelScope.coroutineContext)

    fun navigateBack() {
        _navigation.value = MainNavigationBack
    }

    private val deleteEnabled = MutableLiveData<Boolean>(false)
    fun toggleDelete(enabled: Boolean) {
        deleteEnabled.value = enabled
    }

    val drawerBinding = ItemBinding.of<Component> { itemBinding, _, item ->
        when (item) {
            is ComponentTitle -> itemBinding.set(BR.title, R.layout.drawer_title)
            is ComponentDrawerGroup ->
                itemBinding.set(BR.group, R.layout.drawer_group)
                    .bindExtra(
                        BR.onLongClick,
                        View.OnLongClickListener {
                            toggleDelete(true)
                            true
                        }
                    ).bindExtra(
                        BR.onDelete,
                        View.OnClickListener {
                            if (deleteEnabled.value != true) return@OnClickListener
                            deleteEnabled.value = false
                            viewModelScope.launch(Dispatchers.IO) {
                                db.delete(GroupDTO(item.id, item.name.toString()))
                            }
                        }
                    ).bindExtra(
                        BR.delete,
                        deleteEnabled
                    ).bindExtra(
                        BR.onClick,
                        View.OnClickListener {
                            if (!item.enabled) {
                                viewModelScope.launch(Dispatchers.IO) {
                                    preferenceService.setActiveGroup(item.id)
                                }
                            }
                        }
                    )
            is AddGroupComponent ->
                itemBinding.set(BR.add, R.layout.add_group)
                    .bindExtra(
                        BR.onClick,
                        View.OnClickListener {
                            viewModelScope.launch(Dispatchers.IO) {
                                db.insertAll(GroupDTO(UUID.randomUUID().toString(), item.text.value!!))
                            }
                        }
                    )
        }
    }

    val drawerItems: LiveData<List<Component>> =
        Lives.combineLatest(groups, activeGroup) { groups, active ->
            listOf(
                ComponentTitle(context.getString(R.string.lists)),
            ) + groups.map {
                ComponentDrawerGroup(
                    it.id,
                    it.name.first().toUpperCase() + it.name.drop(1),
                    active == it.id
                )
            } + AddGroupComponent()
        }
}
