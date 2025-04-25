package com.newton.spring_boot_kotlin.controllers

import com.newton.spring_boot_kotlin.database.model.Note
import com.newton.spring_boot_kotlin.database.repository.NoteRepository
import com.newton.spring_boot_kotlin.mappers.toResponse
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/notes")
class NoteController(
    private val noteRepository: NoteRepository
) {

    data class NoteRequest(
        val title: String,
        val content: String,
        val color: Long,
        val id: String?,
    )

    data class NoteResponse(
        val title: String,
        val content: String,
        val color: Long,
        val id: String?,
        val createdAt: Instant
    )

    @PostMapping
    fun save(
        @RequestBody body: NoteRequest): NoteResponse {
        val note = noteRepository.save(
            Note(
                title = body.title,
                content = body.content,
                color = body.color,
                createdAt = Instant.now(),
                ownerId = ObjectId(),
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get()
            )
        )
        return note.toResponse()
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        noteRepository.deleteById(ObjectId(id))
    }

    @GetMapping
    fun findByOwnerId(
        @RequestParam(required = true) ownerId: String
    ) : List<NoteResponse> {
        return noteRepository.findByOwnerId(ObjectId(ownerId)).map {
            it.toResponse()
        }
    }
}