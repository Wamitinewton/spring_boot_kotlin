package com.newton.spring_boot_kotlin.database.repository

import com.newton.spring_boot_kotlin.database.model.User
import jakarta.validation.constraints.Email
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, ObjectId> {
    fun findByEmail(email: String): User?
}