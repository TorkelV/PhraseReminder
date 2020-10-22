package no.lekrot.wordlist.phrases.view

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import no.lekrot.wordlist.R
import no.lekrot.wordlist.databinding.FragmentPhrasesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhrasesFragment : Fragment() {

    private val viewModel: PhrasesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return FragmentPhrasesBinding.inflate(inflater, container, false).apply {
            viewModel = this@PhrasesFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.apply {
            clear()
            add("Search").apply {
                setIcon(R.drawable.ic_baseline_search_24)
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                setOnMenuItemClickListener {
                    viewModel.searchComponent.show()
                    true
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventListeners()
        handleBackPressed()
    }

    private fun handleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (viewModel.canExit.value == true) {
                viewModel.closeAllOverlays()
            } else {
                remove()
            }
        }
    }

    private fun setupEventListeners() {
        viewModel.navigation.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.also { event ->
                when (event) {
                    is PhrasesNavigationToPhrase -> findNavController().navigate(
                        PhrasesFragmentDirections.toPhrase(
                            event.phraseId
                        )
                    )
                }
            }
        }
    }
}
