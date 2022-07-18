package com.manhalrahman.database

import com.manhalrahman.entities.GroupEntity
import com.manhalrahman.entities.Transaction
import com.manhalrahman.entities.User
import com.manhalrahman.entities.UserDraft
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import javax.swing.GroupLayout.Group
import javax.xml.crypto.dsig.TransformService

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

    // user stuff

    fun getUserById(id: Int): DBUserEntry? {
        return ktormDB.sequenceOf(UserTable).firstOrNull { it.userid eq id }
    }

    fun getUsersByName(name: String): DBUserEntry? {
        return ktormDB.sequenceOf(UserTable).firstOrNull { it.name eq name }
    }

    fun addUser(draft: UserDraft): User? {
        val group = getGroupByName(draft.groupName) ?: return null
        val id = ktormDB.insertAndGenerateKey(UserTable) {
            set(it.name, draft.name)
            set(it.groupName, draft.groupName)
            set(it.groupNo, draft.groupNumber)
            set(it.phoneNumber, draft.phoneNumber)
            set(it.email, draft.email)
        }

        return User(
            userId = draft.userId,
            name = draft.name,
            phoneNumber = draft.phoneNumber,
            email = draft.email,
            groupName = draft.groupName,
            groupNo = draft.groupNumber
        )
    }

    // transaction stuff
    fun getTransaction(fromId: Int, toId: Int): Transaction? {
        val query = ktormDB.from(TransactionTable).select().where {
            (TransactionTable.fromId eq fromId) and (TransactionTable.toId eq toId)
        }
        var transaction: Transaction? = null

        for (row in query) {
            transaction = Transaction(
                row[TransactionTable.fromId]!!,
                row[TransactionTable.toId]!!,
                row[TransactionTable.amount]!!
            )
        }
        return transaction
    }


    fun createTransactionInDB(from: User, to: User, amount: Int) {
        val fromId = from.userId
        val toId = to.userId

        ktormDB.insert(TransactionTable) {
            set(it.fromId, fromId)
            set(it.toId, toId)
            set(it.amount, amount)
        }
    }

    fun updateTransaction(transaction: Transaction): Boolean {
        val updatedRows = ktormDB.update(TransactionTable) {
            set(it.fromId, transaction.fromId)
            set(it.toId, transaction.toId)
            set(it.amount, transaction.amount)
            where {
                (TransactionTable.fromId eq transaction.fromId) and (TransactionTable.toId eq transaction.toId)
            }
        }

        return updatedRows > 0
    }

    fun removeUser(id: Int): Boolean {
        val deletedRows = ktormDB.delete(UserTable) { it.userid eq id }
        return deletedRows > 0
    }


    // group stuff
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
                        row[UserTable.phoneNumber]!!,
                        row[UserTable.groupName]!!
                    )
                )
            }
        }
        return userList
    }

    fun getGroupByName(groupName: String): GroupEntity? {
        return ktormDB.sequenceOf(GroupTable).firstOrNull { it.groupName eq groupName }
            ?.let { GroupEntity(it.groupId, it.groupName) } ?: null
    }

    fun addGroup(groupName: String): GroupEntity {
        val id = ktormDB.insertAndGenerateKey(GroupTable) {
            set(it.groupName, groupName)
        } as Int

        return GroupEntity(id, groupName)
    }


}