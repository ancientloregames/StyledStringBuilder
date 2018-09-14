package com.ancientlore.library

import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.text.Annotation
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import java.util.regex.Pattern

class StyledStringBuilder(val text: CharSequence): SpannableString(text) {

	data class Range(val start: Int, val end: Int)

	private val ranges = mutableListOf<Range>()

	private var spanMode = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

	/**
	 * Select exact the range
	 */
	fun forRange(start: Int, end: Int): StyledStringBuilder {
		ranges.clear()
		if (isValidPos(start) && isValidPos(end))
			ranges.add(Range(start, end))
		return this
	}

	/**
	 * Select whole text as range
	 */
	fun forAll(): StyledStringBuilder {
		ranges.clear()
		val range = Range(0, text.length)
		ranges.add(range)
		return this
	}

	/**
	 * Find and store all ranges in the initial string, that represents the exact word
	 */
	fun forAll(word: String): StyledStringBuilder {
		ranges.clear()
		val pattern = Pattern.quote(word)
		ranges.addAll(getRanges(pattern))
		return this
	}

	/**
	 * Find and store all ranges in the initial string, that starts with prefix
	 */
	fun forAllStartWith(prefix: String): StyledStringBuilder {
		ranges.clear()
		val pattern = Pattern.quote(prefix) + "\\w+"
		ranges.addAll(getRanges(pattern))
		return this
	}

	/**
	 * Find and store a range in the initial string, that represents the fisrt occurrence of the word
	 */
	fun forFirst(word: String): StyledStringBuilder {
		ranges.clear()
		val pattern = Pattern.quote(word)
		getRanges(pattern).firstOrNull()?.let {
			ranges.add(it)
		}
		return this
	}

	/**
	 * Find and store a range in the initial string, that represents the fisrt occurrence of the word with prefix
	 */
	fun forFirstWith(prefix: String): StyledStringBuilder {
		ranges.clear()
		val pattern = Pattern.quote(prefix) + "\\w+"
		getRanges(pattern).firstOrNull()?.let {
			ranges.add(it)
		}
		return this
	}

	/**
	 * Applies text color for all ranges, that was previously detected
	 */
	fun applyTextColor(@ColorInt color: Int): StyledStringBuilder {
		ranges.forEach {
			setSpan(ForegroundColorSpan(color), it.start, it.end, spanMode)
		}
		return this
	}

	/**
	 * Applies background color for all ranges, that was previously detected
	 */
	fun applyBackColor(@ColorInt color: Int): StyledStringBuilder {
		ranges.forEach {
			setSpan(BackgroundColorSpan(color), it.start, it.end, spanMode)
		}
		return this
	}

	/**
	 * Makes all ranges, that was previously detected, bold
	 */
	fun makeBold(): StyledStringBuilder {
		ranges.forEach {
			setSpan(StyleSpan(Typeface.BOLD), it.start, it.end, spanMode)
		}
		return this
	}

	/**
	 * Makes all ranges, that was previously detected, italic
	 */
	fun makeItalic(): StyledStringBuilder {
		ranges.forEach {
			setSpan(StyleSpan(Typeface.ITALIC), it.start, it.end, spanMode)
		}
		return this
	}

	/**
	 * Makes all ranges, that was previously detected, normal
	 */
	fun makeNormal(): StyledStringBuilder {
		ranges.forEach {
			setSpan(StyleSpan(Typeface.NORMAL), it.start, it.end, spanMode)
		}
		return this
	}

	/**
	 * Makes all ranges, that was previously detected, underlined
	 */
	fun makeUnderlined(): StyledStringBuilder {
		ranges.forEach {
			setSpan(UnderlineSpan(), it.start, it.end, spanMode)
		}
		return this
	}

	/**
	 * Makes all ranges, that was previously detected, annotated
	 */
	fun annotate(key: String, value: String): StyledStringBuilder {
		ranges.forEach {
			setSpan(Annotation(key, value), it.start, it.end, spanMode)
		}
		return this
	}

	fun setSpanMode(spanMode: Int): StyledStringBuilder {
		this.spanMode = spanMode
		return this
	}

	fun removeStyles(clazz: Class<*>): StyledStringBuilder {
		removeSpan(clazz)
		return this
	}

	private fun getRanges(pattern: String): List<Range> {
		val ranges = mutableListOf<Range>()
		val matcher = Pattern.compile(pattern).matcher(text)
		while (matcher.find()) {
			ranges.add(Range(matcher.start(), matcher.end()))
		}
		return ranges
	}

	private fun isValidPos(pos: Int) = pos >= 0 && text.length > pos
}