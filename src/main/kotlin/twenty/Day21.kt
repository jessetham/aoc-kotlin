package twenty

import util.Resources

typealias Item = Pair<List<String>, List<String>>

fun solve(items: List<Item>) {
    val allIngredients = items.map { it.first }.flatten().associateWith { 0 }.toMutableMap()
    val allAllergens = items.map { it.second }.flatten().associateWith { allIngredients.keys.toMutableSet() }.toMutableMap()
    for ((ingredients, allergens) in items) {
        for (allergen in allergens) {
            allAllergens[allergen] = allAllergens[allergen]!!.intersect(ingredients.toSet()).toMutableSet()
        }
        ingredients.forEach { allIngredients[it] = allIngredients.getOrDefault(it, 0) + 1 }
    }
    val notCulprits = allIngredients.keys.filter { ingredient -> allAllergens.all { c -> !c.value.contains(ingredient) } }.toMutableSet()
    // Part 1
    println(notCulprits.fold(0) { acc, i -> acc + allIngredients[i]!! })
    val narrowed = mutableListOf<Pair<String, String>>()
    while (allAllergens.any { it.value.size > 1 }) {
        for ((allergen, possibles) in allAllergens) {
            possibles.intersect(notCulprits).forEach { possibles.remove(it) }
            if (possibles.size == 1 && possibles.first() !in notCulprits) {
                narrowed.add(possibles.first() to allergen)
                notCulprits.add(possibles.first())
            }
        }
    }
    // Part 2
    println(narrowed.sortedBy { it.second }.joinToString(",") { it.first })
}

fun main() {
    val input = Resources.readFileAsList("input.txt")
    val items = mutableListOf<Item>()
    for (line in input) {
        val (ingredients, allergens) = line.split(" (")
        items.add(ingredients.split(" ") to allergens.substring(9, allergens.length - 1).split(", "))
    }
    solve(items)
}