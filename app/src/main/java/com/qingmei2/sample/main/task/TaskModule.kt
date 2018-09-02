package com.qingmei2.sample.main.task

import org.kodein.di.Kodein
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val TASK_MODULE_TAG = "TASK_MODULE_TAG"

val taskKodeinModule = Kodein.Module(TASK_MODULE_TAG) {

    bind<TaskViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        TaskViewDelegate()
    }
}