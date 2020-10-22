package no.lekrot.wordlist.common.extensions

import androidx.activity.addCallback
import androidx.fragment.app.Fragment

object FragmentExtension {
    fun Fragment.onBackPressed(onBackPressed: () -> Boolean) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (!onBackPressed()) {
                remove()
                requireActivity().onBackPressed()
            }
        }

    }
}