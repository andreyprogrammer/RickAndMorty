package com.example.anderymerkurev.data

fun String.toListId(): List<Int> {
    val list = mutableListOf<Int>()
    this.removeSurrounding("[", "]")
        .split(",")
        .map { it.toInt() }
        .forEach { list.add(it) }
    return list
}

fun Int.toStringId(): String {
    val pageSize = 20
    val results = IntArray(pageSize)
    for (i in 0 until pageSize ) {
        results[i] = this * pageSize - i
    }
    return results.joinToString(prefix = "[", separator = ",", postfix = "]")
}

fun ArrayList<String>.isValuesEmpty(): Boolean {
    var i = 0
    this.forEach { if (it.isEmpty()) i++ }
    return this.size == i
}