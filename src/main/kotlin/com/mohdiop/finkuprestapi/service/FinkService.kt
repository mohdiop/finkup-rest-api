package com.mohdiop.finkuprestapi.service

import com.mohdiop.finkuprestapi.entity.Fink
import com.mohdiop.finkuprestapi.repository.FinkRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FinkService {
    @Autowired
    private lateinit var finkRepository: FinkRepository

    fun connect() = "Connect to server successfully!"

    fun saveFink(fink: Fink): String {
        finkRepository.save(fink)
        return "Fink added successfully!"
    }

    fun saveFinks(finks: List<Fink>): String {
        finkRepository.saveAll(finks)
        return "All finks added successfully!"
    }

    fun getAllFinks(): List<Fink> {
        return finkRepository.findAll()
    }

    fun getFinkById(id: Long): Fink {
        return finkRepository.findById(id).orElse(null)
    }

    fun getFinkByTitle(finkTitle: String): List<Fink> {
        return finkRepository.findByFinkTitle(finkTitle)
    }

    fun getFinkByContent(finkContent: String): List<Fink> {
        return finkRepository.findByFinkContent(finkContent)
    }

    fun updateFink(fink: Fink): String {
        val finkToUpdate = finkRepository.findById(fink.finkId).orElse(null)
        return if (finkToUpdate != null) {
            finkToUpdate.finkTitle = fink.finkTitle
            finkToUpdate.finkContent = fink.finkContent
            finkToUpdate.finkDate = fink.finkDate
            finkRepository.save(finkToUpdate)
            "Fink updated successfully!"
        } else {
            finkRepository.save(fink)
            "This fink is not registered yet so it's been added!"
        }
    }

    fun deleteFink(id: Long): String {
        finkRepository.deleteById(id)
        return "Fink deleted successfully!"
    }

    fun deleteAllFinks(): String{
        finkRepository.deleteAll()
        return "All finks are deleted successfully!"
    }
}