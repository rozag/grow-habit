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

    override val layoutResourceId = R.layout.activity_sections
    override val toolbarTitleId = R.string.app_name
    override val displayHomeAsUp = false
    override val homeButtonEnabled = false

    private val bottomNavigationView by lazy {
        findViewById(R.id.sections_bottom_navigation_view) as BottomNavigationView
    }
    private val contentLayout: ViewGroup by lazy {
        findViewById(R.id.sections_content) as ViewGroup
    }
    private var currentContainer: Container<*, *>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: transparent status bar
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.action_habits
        // TODO: BottomNavigationView items' colors do not change
    }

    override fun onDestroy() {
        super.onDestroy()
        finishContainer()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        showCurrentView()
    }

    private fun showCurrentView() {
        val currentItemId = bottomNavigationView.selectedItemId
        val currentItem = bottomNavigationView.menu.findItem(currentItemId)
        onNavigationItemSelected(currentItem)
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
        val view = AssistantView(this)
        showView(view)
//        initContainer(AssistantContainer(view)) // TODO:
    }

    private fun showHabits() {
        val view = HabitsView(this)
        showView(view)
        initContainer(HabitsContainer(view))
    }

    private fun showStatistics() {
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