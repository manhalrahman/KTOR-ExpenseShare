package com.manhalrahman.entities

data class Transaction(
    val fromId: Int,
    val toId: Int,
    val amount: Int,
)
