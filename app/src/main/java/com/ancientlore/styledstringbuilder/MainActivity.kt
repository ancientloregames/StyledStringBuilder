package com.ancientlore.styledstringbuilder

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ancientlore.library.OnSpanClickListener
import com.ancientlore.library.StyledString
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val styledText = StyledString(textField.text)
				.forFirst("text color")
				.applyTextColor(ContextCompat.getColor(applicationContext, android.R.color.holo_green_dark))
				.forFirst("background")
				.applyBackColor(ContextCompat.getColor(applicationContext, android.R.color.holo_orange_dark))
				.forFirst("bold")
				.makeBold()
				.forFirst("italic")
				.makeItalic()
				.forFirst("underlined")
				.makeUnderlined()
				.forFirst("crossedout")
				.crossOut()
				.forFirst("fonts")
				.setFont("sans-serif-thin")
				.forFirst("rounded")
				.applyBackColor(ContextCompat.getColor(applicationContext, android.R.color.holo_blue_light), 25)
				.forFirst("size")
				.setSizeDp(12)
				.forFirst("subscripts")
				.makeSubscript()
				.forFirst("superscripts")
				.makeSuperscript()
				.forFirst("clickable")
				.doOnClick(textField, object : OnSpanClickListener {
					override fun onClick(span: CharSequence) {
						Toast.makeText(this@MainActivity, R.string.clicked, Toast.LENGTH_SHORT).show()
					}
				})
				.forFirst("outer links")
				.setLink(textField, "https://github.com/ancientloregames")

		textField.text = styledText
	}
}
