package com.ancientlore.library

import android.text.Selection
import android.text.Spannable
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

class TouchableTextViewListener(private val spannable: Spannable): View.OnTouchListener {

	override fun onTouch(v: View, event: MotionEvent): Boolean {
		if (v !is TextView)
			return false

		val action = event.actionMasked
		if ((action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN)
				&& v.paint.measureText(v.text.toString()) >= event.x) {

			val x = event.x.toInt() - v.totalPaddingLeft + v.scrollX
			val y = event.y.toInt() - v.totalPaddingTop + v.scrollY

			val line = v.layout.getLineForVertical(y)
			val offset = v.layout.getOffsetForHorizontal(line, x.toFloat())

			val link = spannable.getSpans(offset, offset, ClickableSpan::class.java)
			if (link.isNotEmpty()) {
				if (action != MotionEvent.ACTION_UP)
					Selection.setSelection(spannable, spannable.getSpanStart(link[0]), spannable.getSpanEnd(link[0]))
				else link[0].onClick(v)

				return true
			}
			else Selection.removeSelection(spannable)
		}

		return false
	}
}