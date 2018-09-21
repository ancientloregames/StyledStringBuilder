package com.ancientlore.library

import android.text.SpannableStringBuilder

fun SpannableStringBuilder.toStyledString() = StyledString(toString())