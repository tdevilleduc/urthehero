package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.config.Mapper
import com.tdevilleduc.urthehero.back.constant.ResilienceConstants.INSTANCE_PERSON_SERVICE
import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.dao.PersonDao
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException
import com.tdevilleduc.urthehero.back.model.Person
import com.tdevilleduc.urthehero.back.model.PersonDTO
import com.tdevilleduc.urthehero.back.service.IPersonService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.MessageFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
class PersonService : IPersonService {
    val logger: Logger = LoggerFactory.getLogger(PersonService::class.java)

    @Autowired
    private lateinit var personDao: PersonDao

    override fun exists(personId: Int): Boolean {
        val person = personDao.findById(personId)
        if (person.isEmpty) {
            logger.error(ApplicationConstants.ERROR_MESSAGE_PERSON_DOESNOT_EXIST, personId)
            return false
        }
        return true
    }

    override fun notExists(personId: Int): Boolean {
        logger.info("person notExists {}", personId)
        return !exists(personId)
    }

    override fun findById(personId: Int): Person {
        val optional = personDao.findById(personId)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw PersonNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PERSON_DOESNOT_EXIST, personId).message)
        }
    }

    override fun findByLogin(userName: String): Person {
        val optional = personDao.findByLogin(userName)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw PersonNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PERSON_LOGIN_DOESNOT_EXIST, userName).message)
        }
    }

    @CircuitBreaker(name = INSTANCE_PERSON_SERVICE, fallbackMethod = "emptyList")
    override fun findAll(): MutableList<Person> {
        return personDao.findAll()
    }

    //NOSONAR - This method is a ChaosMonkey CircuitBreaker fallback method
    private fun emptyList(e: Throwable): MutableList<Person> {
        logger.error("Unable to retrieve list", e)
        return emptyList<Person>().toMutableList()
    }

    override fun createOrUpdate(personDto: PersonDTO): PersonDTO {
        val person: Person = Mapper.convert(personDto)
        return Mapper.convert(personDao.save(person))
    }

    override fun delete(personId: Int) {
        Assert.notNull(personId, ApplicationConstants.CHECK_PERSONID_PARAMETER_MANDATORY!!)
        val person = findById(personId)
        personDao.delete(person)
    }
}