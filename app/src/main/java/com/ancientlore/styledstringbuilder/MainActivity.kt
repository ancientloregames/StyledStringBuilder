package com.ancientlore.styledstringbuilder

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.ancientlore.library.StyledStringBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val styledText = StyledStringBuilder(textField.text)
				.forAllStartWith("O")
				.applyTextColor(ContextCompat.getColor(applicationContext, android.R.color.holo_red_dark))
				.forAllStartWith("R")
				.applyBackColor(ContextCompat.getColor(applicationContext, android.R.color.holo_orange_dark))
				.forAll("them")
				.applyBackColor(ContextCompat.getColor(applicationContext, android.R.color.holo_blue_dark))
				.forAll("all")
				.makeBold()
				.forAllStartWith("th")
				.makeItalic()

		textField.text = styledText
	}
}
