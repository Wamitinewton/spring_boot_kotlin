package com.newton.spring_boot_kotlin.mappers

import com.newton.spring_boot_kotlin.controllers.NoteController
import com.newton.spring_boot_kotlin.database.model.Note

fun Note.toResponse(): NoteController.NoteResponse {
    return NoteController.NoteResponse(
        title = title,
        content = content,
        color = color,
        id = id.toHexString(),
        createdAt = createdAt
    )
}