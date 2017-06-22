package com.vsrstudio.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.vsrstudio.arch.Container
import com.vsrstudio.view.AssistantView
import com.vsrstudio.view.HabitsView
import com.vsrstudio.view.StatisticsView

class SectionsActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun makeIntent(context: Context): Intent {
            val intent = Intent(context, SectionsActivity::class.java)
            return intent
        }
    }

    private val ARG_CURRENT_VIEW = "current_view"

    private val VIEW_ASSISTANT = 0
    private val VIEW_HABITS = 1
    private val VIEW_STATISTICS = 2

    override val layoutResourceId = R.layout.activity_sections
    override val toolbarTitleId = R.string.app_name
    override val displayHomeAsUp = false
    override val homeButtonEnabled = false

    private val bottomNavigationView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.sections_bottom_navigation_view) as BottomNavigationView
    }
    private val contentLayout: ViewGroup by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.sections_content) as ViewGroup
    }
    private var currentContainer: Container<*, *>? = null
    private var currentView: Int = VIEW_HABITS

    // TODO: transparent status bar
    // TODO: BottomNavigationView items' colors do not change
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        currentView = savedInstanceState?.getInt(ARG_CURRENT_VIEW, VIEW_HABITS) ?: VIEW_HABITS
        showSelectedView()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(ARG_CURRENT_VIEW, currentView)
    }

    override fun onDestroy() {
        super.onDestroy()
        finishContainer()
    }

    private fun showSelectedView() {
        val itemId = when (currentView) {
            VIEW_ASSISTANT -> R.id.action_assistant
            VIEW_HABITS -> R.id.action_habits
            else -> R.id.action_statistics
        }
        bottomNavigationView.selectedItemId = itemId
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

    private fun initContainer(container: Container<*, *>) {
        finishContainer()
        currentContainer = container
        container.init()
    }

    private fun finishContainer() {
        currentContainer?.finish()
    }

    private fun showAssistant() {
        currentView = VIEW_ASSISTANT
        val view = AssistantView(this)
        showView(view)
//        initContainer(AssistantContainer(view)) // TODO:
    }

    private fun showHabits() {
        currentView = VIEW_HABITS
        val view = HabitsView(this)
        showView(view)
        initContainer(HabitsContainer(App.instance.appContainer, view))
    }

    private fun showStatistics() {
        currentView = VIEW_STATISTICS
        val view = StatisticsView(this)
        showView(view)
//        initContainer(StatisticsContainer(view)) // TODO:
    }

    private fun showView(view: View) {
        contentLayout.run {
            if (childCount != 0) {
                removeViewAt(0)
            }
            addView(view)
        }
    }

}