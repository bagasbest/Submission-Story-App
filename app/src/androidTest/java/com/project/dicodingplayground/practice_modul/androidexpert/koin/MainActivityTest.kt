package com.project.dicodingplayground.practice_modul.androidexpert.koin

import org.junit.After
import org.junit.Before
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals


class MainActivityTest: KoinTest {

    private val userRepository: UserRepository by inject()
    private val username = "dicoding"

    @Before
    fun setUp() {
        loadKoinModules(storageModule)
        userRepository.loginUser(username)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getUsernameFromRepository() {
        val requestedUsername = userRepository.getUser()
        assertEquals(username, requestedUsername)
    }
}