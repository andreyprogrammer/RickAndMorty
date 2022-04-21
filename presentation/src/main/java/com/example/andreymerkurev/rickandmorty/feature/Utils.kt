package com.example.andreymerkurev.rickandmorty.feature

fun List<String>.toElementsSet(): String {
    val results = IntArray(this.size)
    var i = 0
    this.forEach {
        results[i++] = it.takeLastInt()
    }
    return results.joinToString(prefix = "[", separator = ",", postfix = "]")
}

fun String.takeLastInt(): Int {
     return this.split("/").takeLast(1)[0].toInt()
}