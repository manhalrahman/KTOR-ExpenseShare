package com.manhalrahman.database

import com.manhalrahman.entities.Transaction
import com.manhalrahman.entities.User
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf

class DatabaseManager {
    private val hostname = "localhost"
    private val databaseName = "Ktor_expense_share"
    private val username = "root"
    private val password = "8tqj6kjs"

    private val ktormDB: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktormDB = Database.connect(jdbcUrl)
    }

    fun getUsersInGroup(groupNo: Int): List<User> {
        val userList = mutableListOf<User>()
        for (row in ktormDB.from(UserTable).select()) {
            if (row[UserTable.groupNo] == groupNo) {
                userList.add(
                    User(
                        row[UserTable.userid]!!,
                        row[UserTable.groupNo]!!,
                        row[UserTable.name]!!,
                        row[UserTable.email]!!,
                        row[UserTable.phoneNumber]!!
                    )
                )
            }
        }
        return userList
    }

    fun getUserById(id: Int): DBUserEntry? {
        return ktormDB.sequenceOf(UserTable).firstOrNull{it.userid eq id}
    }

    fun getUsersByName(name: String): DBUserEntry? {
        return ktormDB.sequenceOf(UserTable).firstOrNull{it.name eq name}
    }

    fun getTransaction(fromId: Int, toId: Int): Transaction? {
        val query = ktormDB.from(TransactionTable).select().where{
            (TransactionTable.fromId eq fromId) and (TransactionTable.toId eq toId)
        }
        var transaction: Transaction? = null

        for(row in query) {
            transaction = Transaction(
                row[TransactionTable.fromId]!!,
                row[TransactionTable.toId]!!,
                row[TransactionTable.amount]!!
            )
        }
        return transaction
    }




}