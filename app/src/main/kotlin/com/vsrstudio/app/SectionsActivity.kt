package com.vsrstudio.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem

class SectionsActivity : BaseActivity(),
        BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun makeIntent(context: Context): Intent {
            val intent = Intent(context, SectionsActivity::class.java)
            return intent
        }
    }

    override val layoutResourceId = R.layout.activity_sections
    override val toolbarTitleId = R.string.app_name
    override val displayHomeAsUp = false
    override val homeButtonEnabled = false

    private val bottomNavigationView by lazy {
        findViewById(R.id.sections_bottom_navigation_view) as BottomNavigationView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: transparent status bar
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.action_habits
        // TODO: BottomNavigationView items' colors do not change
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_assistant -> {
            showAssistant()
            true
        }
        R.id.action_habits -> {
            showHabits()
            true
        }
        R.id.action_statistics -> {
            showStatistics()
            true
        }
        else -> false
    }

    private fun showAssistant() {
        // TODO:
    }

    private fun showHabits() {
        // TODO:
    }

    private fun showStatistics() {
        // TODO:
    }

}