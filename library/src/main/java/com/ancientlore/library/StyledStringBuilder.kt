package com.ancientlore.library

import android.text.SpannableString
import java.util.regex.Pattern

class StyledStringBuilder(val text: CharSequence): SpannableString(text) {
	private val ranges = mutableListOf<Range>()

	/**
	 * Find and store all ranges in the initial string, that starts with prefix
	 */
	fun forAllStartWith(prefix: String): StyledStringBuilder {
		ranges.clear()
		val pattern = Pattern.quote(prefix) + "\\w+"
		ranges.addAll(getRanges(pattern))
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