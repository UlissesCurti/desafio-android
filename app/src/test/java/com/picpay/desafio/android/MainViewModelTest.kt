package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.core.ViewState
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// When the tests are running all together, sometimes there are random failures. If there are
// running one by one, everything works perfectly.
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var viewModel: MainViewModel

    @MockK(relaxed = true)
    private lateinit var repository: UserRepository

    @MockK(relaxed = true)
    private lateinit var observer: Observer<ViewState<List<User>>>

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockKAnnotations.init(this)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `test loadUsers on success`() {
        // cache mock
        every { repository.getUsers() } returns mockCacheUsers()
        // request mock
        every { repository.requestUsers() } returns mockRequestUsers()

        // capture the liveData values to check the changes
        val slot = slot<ViewState<List<User>>>()
        val liveDataValues = mutableListOf<ViewState<List<User>>>()
        viewModel.usersLiveData.observeForever(observer)
        every { observer.onChanged(capture(slot)) } answers {
            liveDataValues.add(slot.captured)
        }

        viewModel.loadUsers()

        // check the called methods
        verify {
            repository.getUsers()
            repository.requestUsers()
            runBlocking {
                repository.deleteAllUsers()
                repository.saveUsers(mockRequestUsers().blockingGet())
            }
        }

        // check the liveData values
        assertEquals(
            liveDataValues,
            mutableListOf(
                ViewState(status = ViewState.Status.LOADING),
                ViewState(
                    status = ViewState.Status.CACHE,
                    data = runBlocking { mockCacheUsers().toList().first() }
                ),
                ViewState(
                    status = ViewState.Status.SUCCESS,
                    data = mockRequestUsers().blockingGet()
                )
            )
        )
    }

    @Test
    fun `test loadUsers on error`() {
        // cache mock
        every { repository.getUsers() } returns mockCacheUsers()
        // request mock
        every { repository.requestUsers() } returns Single.error(Throwable("test error"))

        // capture the liveData values to check the changes
        val slot = slot<ViewState<List<User>>>()
        val liveDataValues = mutableListOf<ViewState<List<User>>>()
        viewModel.usersLiveData.observeForever(observer)
        every { observer.onChanged(capture(slot)) } answers {
            liveDataValues.add(slot.captured)
        }

        viewModel.loadUsers()

        // check the called methods
        verify {
            repository.getUsers()
            repository.requestUsers()
        }

        // verify methods that should not be called
        verify(exactly = 0) {
            runBlocking {
                repository.deleteAllUsers()
                repository.saveUsers(mockRequestUsers().blockingGet())
            }
        }

        // check the liveData values
        assertEquals(
            liveDataValues,
            mutableListOf(
                ViewState(status = ViewState.Status.LOADING),
                ViewState(
                    status = ViewState.Status.CACHE,
                    data = runBlocking { mockCacheUsers().toList().first() }
                ),
                ViewState(
                    status = ViewState.Status.ERROR
                )
            )
        )
    }

    @Test
    fun `test saveScrollPosition then update scrollPosition`() {
        viewModel.saveScrollPosition(123)

        assertEquals(123, viewModel.scrollPosition)
    }

    private fun mockCacheUsers(): Flow<List<User>> {
        return listOf(
            listOf(
                mockUser(1)
            )
        ).asFlow()
    }

    private fun mockRequestUsers(): Single<List<User>> {
        return Single.just(
            listOf(
                mockUser(1),
                mockUser(2),
                mockUser(3)
            )
        )
    }

    private fun mockUser(id: Int) = User(
        img = "img test",
        name = "name test",
        id = id,
        username = "username test"
    )

}