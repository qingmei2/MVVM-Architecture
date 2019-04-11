package com.github.qingmei2.autodispose.recyclerview

import com.github.qingmei2.autodispose.recyclerview.AutoDisposeViewHolder
import io.reactivex.Observable

interface AutoDisposeViewHolderEventsProvider {

    fun providesObservable(): Observable<AutoDisposeViewHolder.ViewHolderEvent>
}