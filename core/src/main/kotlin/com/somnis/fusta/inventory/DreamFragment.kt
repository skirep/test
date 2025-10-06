package com.somnis.fusta.inventory

/**
 * Represents a fragment of a dream that Nil needs to collect
 */
data class DreamFragment(
    val id: String,
    val name: String,
    val description: String,
    val levelOrigin: String
)
