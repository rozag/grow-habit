package com.vsrstudio.growhabit.app.assistant

import com.vsrstudio.growhabit.arch.ArchBaseFragment

class AssistantFragment : ArchBaseFragment<AssistantController, AssistantReducer, AssistantViewState>() {

    override val controller: AssistantController = AssistantController()
    override val reducer: AssistantReducer = AssistantReducer(this)

    override fun updateViewState(viewState: AssistantViewState) {
        // TODO:
    }

}