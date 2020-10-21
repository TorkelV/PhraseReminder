package no.lekrot.wordlist.common.service

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceService(context: Context) {

    private val dataStore: DataStore<Preferences> by lazy {
        context.createDataStore(
            name = "settings"
        )
    }

    val activegroupKey = preferencesKey<String>("active_group")
    val activeGroup: Flow<String> = dataStore.data.map { prefs ->
        prefs[activegroupKey] ?: "1"
    }

    suspend fun setActiveGroup(id: String) {
        dataStore.edit { settings ->
            settings[activegroupKey] = id
        }
    }

    val isAppOnboardedKey = preferencesKey<Boolean>("app_onboarded")
    val isAppOnboarded: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[isAppOnboardedKey] ?: false
    }

    suspend fun completeOnboarding() {
        dataStore.edit { settings ->
            settings[isAppOnboardedKey] = true
        }
    }
}
