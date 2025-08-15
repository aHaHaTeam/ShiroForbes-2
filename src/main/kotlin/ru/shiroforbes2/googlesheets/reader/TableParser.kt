package ru.shiroforbes2.googlesheets.reader

interface TableParser<T> {
  fun parse(table: List<List<String>>): List<T>

  fun joinAndParse(tables: List<List<List<String>>>): List<T> {
    val shrankTables =
      tables
        .map { table ->
          val width = table.maxOf { it.size }
          table
            .takeWhile { it.isNotEmpty() }
            .map { it + List(width - it.size) { " " } }
        }.reduce { table1, table2 ->
          table1.zip(table2).map { it.first + it.second }
        }
    return parse(shrankTables)
  }
}
