package com.artemissoftware.orpheusplaylist.utils.extensions

fun Int.toDuration(): String {
    var result = ""
    var secondsString = ""
    val hours = (this / (1000 * 60 * 60))
    val minutes = (this % (1000 * 60 * 60)) / (1000 * 60)
    val seconds = (this % (1000 * 60 * 60) % (1000 * 60) / 1000)

    if (hours > 0) {
        result = "$hours:"
    }
    secondsString = if (seconds < 10) {
        "0$seconds"
    } else {
        "$seconds"
    }
    result = "$result$minutes:$secondsString"
    return result
}
