package com.filous.getjokeapp

data class DataType(
    val category: String,
    val error: Boolean,
    val flags: Flags,
    val id: Int,
    val joke: String,
    val setup: String,
    val delivery: String,
    val lang: String,
    val safe: Boolean,
    val type: String
)