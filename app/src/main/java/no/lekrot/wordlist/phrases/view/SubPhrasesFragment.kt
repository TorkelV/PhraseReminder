package no.lekrot.wordlist.phrases.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import no.lekrot.wordlist.common.extensions.FragmentExtension.onBackPressed
import no.lekrot.wordlist.databinding.FragmentSubPhrasesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SubPhrasesFragment : Fragment() {

    private val args by navArgs<SubPhrasesFragmentArgs>()

    private val vm: SubPhrasesViewModel by viewModel {
        parametersOf(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSubPhrasesBinding.inflate(inflater, container, false).apply {
        viewModel = vm
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBackPressed()
    }

    private fun handleBackPressed() {
        vm.canLeave.observe(viewLifecycleOwner) {}
        onBackPressed {
            (vm.canLeave.value ?: true).not().also { staying ->
                if (staying) vm.prepareToLeave()
            }
        }
    }
}
