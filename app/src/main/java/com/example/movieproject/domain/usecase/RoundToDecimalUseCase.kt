package com.example.movieproject.domain.usecase

import kotlin.math.pow
import kotlin.math.round

class RoundToDecimalUseCase(private val doubleValue: Double) {

    operator fun invoke(decimalPlaces: Int = 1): Double {
        val multiplier = 10.0.pow(decimalPlaces)
        return round(doubleValue * multiplier) / multiplier
    }
}
