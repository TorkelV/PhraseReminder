package no.lekrot.wordlist.main.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import no.lekrot.wordlist.R
import no.lekrot.wordlist.databinding.ActivityMainBinding
import no.lekrot.wordlist.main.MainNavigationBack
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Shortcut)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val navController = Navigation.findNavController(this, R.id.menu_nav_host).apply {
            addOnDestinationChangedListener { _, destination, _ ->
                viewModel.currentFragment.postValue(destination.id)
            }
        }
        AppBarConfiguration(setOf(R.id.phrases_fragment), binding.drawer).also {
            binding.toolbar.setupWithNavController(navController, it)
        }
        viewModel.navigation.observe(this) {
            it.getContentIfNotHandled()?.also { event ->
                when (event) {
                    is MainNavigationBack -> onBackPressed()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }
}
