// DrinkExtensions.kt
package com.angelemv.android.pruebaintercamaemv.models.data

import android.os.Bundle

fun Drink.toBundle(): Bundle {
    return Bundle().apply {
        putString("idDrink", idDrink)
        putString("strDrink", strDrink)
        putString("strDrinkAlternate", strDrinkAlternate)
        putString("strTags", strTags)
        putString("strVideo", strVideo)
        putString("strCategory", strCategory)
        putString("strIBA", strIBA)
        putString("strAlcoholic", strAlcoholic)
        putString("strGlass", strGlass)
        putString("strInstructions", strInstructions)
        putString("strInstructionsES", strInstructionsES)
        putString("strInstructionsDE", strInstructionsDE)
        putString("strInstructionsFR", strInstructionsFR)
        putString("strInstructionsIT", strInstructionsIT)
        putString("strInstructionsZH_HANS", strInstructionsZH_HANS)
        putString("strInstructionsZH_HANT", strInstructionsZH_HANT)
        putString("strDrinkThumb", strDrinkThumb)
        putString("strIngredient1", strIngredient1)
        putString("strIngredient2", strIngredient2)
        putString("strIngredient3", strIngredient3)
        putString("strIngredient4", strIngredient4)
        putString("strIngredient5", strIngredient5)
        putString("strIngredient6", strIngredient6)
        putString("strIngredient7", strIngredient7)
        putString("strIngredient8", strIngredient8)
        putString("strIngredient9", strIngredient9)
        putString("strIngredient10", strIngredient10)
        putString("strIngredient11", strIngredient11)
        putString("strIngredient12", strIngredient12)
        putString("strIngredient13", strIngredient13)
        putString("strIngredient14", strIngredient14)
        putString("strIngredient15", strIngredient15)
        putString("strMeasure1", strMeasure1)
        putString("strMeasure2", strMeasure2)
        putString("strMeasure3", strMeasure3)
        putString("strMeasure4", strMeasure4)
        putString("strMeasure5", strMeasure5)
        putString("strMeasure6", strMeasure6)
        putString("strMeasure7", strMeasure7)
        putString("strMeasure8", strMeasure8)
        putString("strMeasure9", strMeasure9)
        putString("strMeasure10", strMeasure10)
        putString("strMeasure11", strMeasure11)
        putString("strMeasure12", strMeasure12)
        putString("strMeasure13", strMeasure13)
        putString("strMeasure14", strMeasure14)
        putString("strMeasure15", strMeasure15)
        putString("strImageSource", strImageSource)
        putString("strImageAttribution", strImageAttribution)
        putString("strCreativeCommonsConfirmed", strCreativeCommonsConfirmed)
        putString("dateModified", dateModified)
    }
}

fun Bundle.toDrink(): Drink {
    return Drink(
        idDrink = getString("idDrink") ?: "",
        strDrink = getString("strDrink") ?: "",
        strDrinkAlternate = getString("strDrinkAlternate"),
        strTags = getString("strTags"),
        strVideo = getString("strVideo"),
        strCategory = getString("strCategory") ?: "",
        strIBA = getString("strIBA"),
        strAlcoholic = getString("strAlcoholic") ?: "",
        strGlass = getString("strGlass") ?: "",
        strInstructions = getString("strInstructions") ?: "",
        strInstructionsES = getString("strInstructionsES"),
        strInstructionsDE = getString("strInstructionsDE"),
        strInstructionsFR = getString("strInstructionsFR"),
        strInstructionsIT = getString("strInstructionsIT"),
        strInstructionsZH_HANS = getString("strInstructionsZH_HANS"),
        strInstructionsZH_HANT = getString("strInstructionsZH_HANT"),
        strDrinkThumb = getString("strDrinkThumb"),
        strIngredient1 = getString("strIngredient1"),
        strIngredient2 = getString("strIngredient2"),
        strIngredient3 = getString("strIngredient3"),
        strIngredient4 = getString("strIngredient4"),
        strIngredient5 = getString("strIngredient5"),
        strIngredient6 = getString("strIngredient6"),
        strIngredient7 = getString("strIngredient7"),
        strIngredient8 = getString("strIngredient8"),
        strIngredient9 = getString("strIngredient9"),
        strIngredient10 = getString("strIngredient10"),
        strIngredient11 = getString("strIngredient11"),
        strIngredient12 = getString("strIngredient12"),
        strIngredient13 = getString("strIngredient13"),
        strIngredient14 = getString("strIngredient14"),
        strIngredient15 = getString("strIngredient15"),
        strMeasure1 = getString("strMeasure1"),
        strMeasure2 = getString("strMeasure2"),
        strMeasure3 = getString("strMeasure3"),
        strMeasure4 = getString("strMeasure4"),
        strMeasure5 = getString("strMeasure5"),
        strMeasure6 = getString("strMeasure6"),
        strMeasure7 = getString("strMeasure7"),
        strMeasure8 = getString("strMeasure8"),
        strMeasure9 = getString("strMeasure9"),
        strMeasure10 = getString("strMeasure10"),
        strMeasure11 = getString("strMeasure11"),
        strMeasure12 = getString("strMeasure12"),
        strMeasure13 = getString("strMeasure13"),
        strMeasure14 = getString("strMeasure14"),
        strMeasure15 = getString("strMeasure15"),
        strImageSource = getString("strImageSource"),
        strImageAttribution = getString("strImageAttribution"),
        strCreativeCommonsConfirmed = getString("strCreativeCommonsConfirmed"),
        dateModified = getString("dateModified")
    )
}
// DrinkExtensions.kt
fun Drink.getFormattedIngredients(): String {
    val ingredients = listOfNotNull(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4,
        strIngredient5, strIngredient6, strIngredient7, strIngredient8,
        strIngredient9, strIngredient10, strIngredient11, strIngredient12,
        strIngredient13, strIngredient14, strIngredient15
    )

    return ingredients.joinToString(separator = "\n") { "• $it" }
}
