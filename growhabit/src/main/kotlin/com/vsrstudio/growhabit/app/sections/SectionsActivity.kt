package com.vsrstudio.growhabit.app.sections

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.vsrstudio.growhabit.R
import com.vsrstudio.growhabit.app.assistant.AssistantFragment
import com.vsrstudio.growhabit.app.habits.HabitsFragment
import com.vsrstudio.growhabit.app.statistics.StatisticsFragment
import com.vsrstudio.growhabit.arch.ArchBaseActivity

class SectionsActivity : ArchBaseActivity<SectionsController, SectionsReducer, SectionsViewState>(),
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

    override val controller: SectionsController = SectionsController()
    override val reducer: SectionsReducer = SectionsReducer(this, controller)

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

    override fun updateViewState(viewState: SectionsViewState) = when (viewState) {
        SectionsViewState.AssistantState -> showAssistant()
        SectionsViewState.HabitsState -> showHabits()
        SectionsViewState.StatisticsState -> showStatistics()
    }

    private fun showAssistant() = showFragment(AssistantFragment())

    private fun showHabits() = showFragment(HabitsFragment())

    private fun showStatistics() = showFragment(StatisticsFragment())

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.sections_content, fragment)
                .commit()
    }

}