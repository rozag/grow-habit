package com.vsrstudio.controller

import com.vsrstudio.arch.Controller
import com.vsrstudio.entity.useraction.HabitsAction
import io.reactivex.Observable

class HabitsController : Controller<HabitsAction> {

    override fun subscribeOnUserActions(observable: Observable<HabitsAction>) {
        // TODO
    }

    override fun unsubscribeFromUserActions() {
        // TODO
    }

}