package com.example.movieproject.data.usecase

class RoundToDecimalUseCase(
    private val doubleValue: Double
) {

    operator fun invoke( decimalPlaces: Int = 1): Double {
        val multiplier = Math.pow(10.0, decimalPlaces.toDouble())
        val roundedValue = Math.round(doubleValue * multiplier) / multiplier
        return roundedValue
    }

}