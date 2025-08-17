package ru.shiroforbes2.entity

enum class Area {
  Algebra,
  NumberTheory,
  Combinatorics,
  Geometry;

}

fun String.toArea(): Area? =
  when (this) {
    "алг" -> Area.Algebra
    "тч" -> Area.NumberTheory
    "комба" -> Area.Combinatorics
    "геома" -> Area.Geometry
    else -> null
  }
