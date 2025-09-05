package com.mohdiop.finkuprestapi.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "fink")
data class Fink(
    @Id
    var finkId: Long,
    var finkTitle: String,
    var finkContent: String,
    var finkDate: Long = System.currentTimeMillis()
)
