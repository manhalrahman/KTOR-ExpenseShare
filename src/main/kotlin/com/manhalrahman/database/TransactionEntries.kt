package com.manhalrahman.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int

interface DBTransactionEntity: Entity<DBTransactionEntity> {
    companion object: Entity.Factory<DBUserEntry>()
    val toId:Int
    val fromId:Int
    var amount:Int
}

object TransactionTable: Table<DBTransactionEntity>("transaction") {
    val fromId = int("fromId").bindTo{it.fromId}
    val toId = int("toId").bindTo{it.toId}
    val amount = int("amount").bindTo{it.amount}
}
