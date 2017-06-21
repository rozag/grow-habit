package com.vsrstudio.controller

import com.vsrstudio.arch.Controller
import com.vsrstudio.entity.useraction.HabitsUserAction
import io.reactivex.Observable

class HabitsController : Controller<HabitsUserAction> {

    override fun subscribeOnUserActions(observable: Observable<HabitsUserAction>) {
        // TODO
    }

    override fun unsubscribeFromUserActions() {
        // TODO
    }

}