package com.mohdiop.finkuprestapi.service

import com.mohdiop.finkuprestapi.entity.Fink
import com.mohdiop.finkuprestapi.repository.FinkRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FinkService {
    @Autowired
    private lateinit var finkRepository: FinkRepository

    fun saveFink(fink: Fink): Fink {
        return finkRepository.save(fink)
    }

    fun saveFinks(finks: List<Fink>): List<Fink> {
        return finkRepository.saveAll(finks)
    }

    fun getAllFinks(): List<Fink> {
        return finkRepository.findAll()
    }

    fun getFinkById(id: Long): Fink {
        return finkRepository.findById(id).orElse(null)
    }

    fun getFinkByTitle(finkTitle: String): Fink {
        return finkRepository.findByFinkTitle(finkTitle)
    }

    fun getFinkByContent(finkContent: String): Fink {
        return finkRepository.findByFinkContent(finkContent)
    }

    fun updateFink(fink: Fink): Fink? {
        val finkToUpdate = finkRepository.findById(fink.finkId).orElse(null)
        return if (finkToUpdate != null) {
            finkToUpdate.finkTitle = fink.finkTitle
            finkToUpdate.finkContent = fink.finkContent
            finkToUpdate.finkDate = fink.finkDate
            finkRepository.save(finkToUpdate)
        } else {
            null
        }
    }

    fun deleteFink(id: Long): String {
        finkRepository.deleteById(id)
        return "Fink deleted successfully"
    }

    fun deleteAllFinks(): String{
        finkRepository.deleteAll()
        return "All finks are deleted successfully"
    }
}