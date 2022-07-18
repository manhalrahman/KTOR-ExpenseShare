package com.manhalrahman.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar


object UserTable: Table<DBUserEntry>("UserInfoTable") {
    val userid = int("userid").primaryKey().bindTo{it.userId}
    val name = varchar("name").bindTo{it.name}
    val email = varchar("email").bindTo{it.email}
    val phoneNumber = varchar("phoneNumber").bindTo{it.phoneNumber}
    val groupNo = int("groupNumber").bindTo{it.groupNo}
    val groupName = varchar("groupName").bindTo{it.groupName}
}

interface DBUserEntry: Entity<DBUserEntry>{
    companion object: Entity.Factory<DBUserEntry>()
    val userId: Int
    val groupNo: Int
    val name: String
    val email: String
    val phoneNumber: String
    val groupName: String
}