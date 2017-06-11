package com.vsrstudio.growhabit.app.assistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsrstudio.growhabit.R
import com.vsrstudio.growhabit.arch.ArchBaseFragment

class AssistantFragment : ArchBaseFragment<AssistantController, AssistantReducer, AssistantViewState>() {

    override val controller: AssistantController = AssistantController()
    override val reducer: AssistantReducer = AssistantReducer(this)

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_assistant, container, false)
    }

    override fun updateViewState(viewState: AssistantViewState) {
        // TODO:
    }

}