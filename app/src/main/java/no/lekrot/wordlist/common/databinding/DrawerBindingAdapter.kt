package no.lekrot.wordlist.common.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout

object DrawerBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["onClose"], requireAll = false)
    fun DrawerLayout.onClose(onClose: () -> Unit) {
        this.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {
                onClose()
            }
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }
}
