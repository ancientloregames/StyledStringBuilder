package com.ancientlore.library

import android.support.annotation.ColorInt
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import java.util.regex.Pattern

class StyledStringBuilder(val text: CharSequence): SpannableString(text) {
	private val ranges = mutableListOf<Range>()

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
	 * Applies text color for all ranges, that was previously detected
	 */
	fun applyTextColor(@ColorInt color: Int): StyledStringBuilder {
		ranges.forEach {
			setSpan(ForegroundColorSpan(color), it.start, it.end, SPAN_EXCLUSIVE_EXCLUSIVE)
		}
		return this
	}

	/**
	 * Applies background color for all ranges, that was previously detected
	 */
	fun applyBackColor(@ColorInt color: Int): StyledStringBuilder {
		ranges.forEach {
			setSpan(BackgroundColorSpan(color), it.start, it.end, SPAN_EXCLUSIVE_EXCLUSIVE)
		}
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

	data class Range(val start: Int, val end: Int)
}