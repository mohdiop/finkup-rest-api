package com.mohdiop.finkuprestapi.controller

import com.mohdiop.finkuprestapi.entity.Fink
import com.mohdiop.finkuprestapi.service.FinkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FinkController {

    @Autowired
    private lateinit var finkService: FinkService

    @GetMapping("/")
    fun successToConnect() = "Connect to server successfully"

    @PostMapping("/addFink")
    fun addFink(@RequestBody fink: Fink): Fink {
        return finkService.saveFink(fink)
    }

    @PostMapping("/addFinks")
    fun addFinks(@RequestBody finks: List<Fink>): List<Fink> {
        return finkService.saveFinks(finks)
    }

    @GetMapping("/finks")
    fun findAllFinks(): List<Fink> {
        return finkService.getAllFinks()
    }

    @GetMapping("/finkId/{id}")
    fun findFinkById(@PathVariable id: Long): Fink {
        return finkService.getFinkById(id)
    }

    @GetMapping("/finkTitle/{title}")
    fun findFinkByTitle(@PathVariable title: String): Fink {
        return finkService.getFinkByTitle(title)
    }

    @GetMapping("/finkContent/{content}")
    fun findFinkByContent(@PathVariable content: String): Fink {
        return finkService.getFinkByContent(content)
    }

    @PutMapping("/update")
    fun updateFink(@RequestBody fink: Fink): Fink {
        return finkService.updateFink(fink)!!
    }

    @DeleteMapping("/delete/{id}")
    fun deleteFink(@PathVariable id: Long): String {
        return finkService.deleteFink(id)
    }

    @DeleteMapping("/delete")
    fun deleteAllFinks(): String{
        return finkService.deleteAllFinks()
    }
}