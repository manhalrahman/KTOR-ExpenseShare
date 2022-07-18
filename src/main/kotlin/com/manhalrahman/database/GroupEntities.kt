package com.manhalrahman.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface DBGroupEntity : Entity<DBGroupEntity> {
    companion object: Entity.Factory<DBUserEntry>()
    val groupId: Int
    val groupName: String
}

object GroupTable: Table<DBGroupEntity>("groupTable") {
    val groupId = int("groupId").primaryKey().bindTo{it.groupId}
    val groupName = varchar("groupName").primaryKey().bindTo{it.groupName}
}