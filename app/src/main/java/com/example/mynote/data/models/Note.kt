package com.example.mynote.data.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Note(
    @DocumentId val id: String = "",
    @ServerTimestamp val createAt: Date = Date(),
    val title: String = "",
    val content: String = "",
    val userId: String = "",
    val done: Boolean = false
)
