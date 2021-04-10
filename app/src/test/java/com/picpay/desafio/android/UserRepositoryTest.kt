package com.picpay.desafio.android

import com.nhaarman.mockitokotlin2.any
import com.picpay.desafio.android.core.Configuration
import com.picpay.desafio.android.data.local.UsersDao
import com.picpay.desafio.android.data.network.UserService
import com.picpay.desafio.android.data.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var userRepository: UserRepository

    @MockK(relaxed = true)
    private lateinit var configuration: Configuration

    @MockK(relaxed = true)
    private lateinit var usersDao: UsersDao

    @MockK(relaxed = true)
    private lateinit var userService: UserService

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        every { configuration.getService() } returns userService

        userRepository = UserRepository(configuration, usersDao)
    }

    @Test
    fun `test requestUsers`() {
        userRepository.requestUsers()

        verify {
            userService.requestUsers()
        }
    }

    @Test
    fun `test getUsers`() {
        userRepository.getUsers()

        verify {
            usersDao.getAllUsers()
        }
    }

    @Test
    fun `test deleteAllUsers`() = runBlocking {
        userRepository.deleteAllUsers()

        verify {
            runBlocking {
                usersDao.deleteAll()
            }
        }
    }

    @Test
    fun `test saveUsers`() = runBlocking {
        userRepository.saveUsers(any())

        verify {
            runBlocking {
                usersDao.saveUsers(any())
            }
        }
    }
}