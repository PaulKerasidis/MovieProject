package com.example.movieproject.domain.usecase

class DateFormatUseCase(private val date: String) {

    operator fun invoke(): String {
        val day = date.substring(8, 10)
        val month = date.substring(5, 7).toMonth()
        val year = date.substring(0, 4)
        return "$day $month $year"
    }

    private fun String.toMonth(): String = when (this) {
        "01" -> "Jan"; "02" -> "Feb"; "03" -> "Mar"; "04" -> "Apr"
        "05" -> "May"; "06" -> "Jun"; "07" -> "Jul"; "08" -> "Aug"
        "09" -> "Sep"; "10" -> "Oct"; "11" -> "Nov"; else -> "Dec"
    }
}
