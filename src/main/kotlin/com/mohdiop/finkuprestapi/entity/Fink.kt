package com.mohdiop.finkuprestapi.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fink")
data class Fink(
    @Id
    @GeneratedValue
    var finkId: Long,
    var finkTitle: String,
    var finkContent: String,
    var finkDate: Long = System.currentTimeMillis()
)
