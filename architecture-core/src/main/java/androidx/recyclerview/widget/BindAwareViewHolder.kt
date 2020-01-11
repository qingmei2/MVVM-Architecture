package androidx.recyclerview.widget

import android.view.View

abstract class BindAwareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind()

    abstract fun onUnbind()

    internal override fun setFlags(flags: Int, mask: Int) {
        val wasBound = isBound
        super.setFlags(flags, mask)
        notifyBinding(wasBound, isBound)
    }

    internal override fun addFlags(flags: Int) {
        val wasBound = isBound
        super.addFlags(flags)
        notifyBinding(wasBound, isBound)
    }

    internal override fun clearPayload() {
        val wasBound = isBound
        super.clearPayload()
        notifyBinding(wasBound, isBound)
    }

    internal override fun resetInternal() {
        val wasBound = isBound
        super.resetInternal()
        notifyBinding(wasBound, isBound)
    }

    private fun notifyBinding(previousBound: Boolean, currentBound: Boolean) {
        if (previousBound && !currentBound) {
            onUnbind()
        } else if (!previousBound && currentBound) {
            onBind()
        }
    }
}