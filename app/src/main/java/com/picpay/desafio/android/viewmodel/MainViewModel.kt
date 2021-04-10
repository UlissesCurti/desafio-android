package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.core.ViewState
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val disposable by lazy { CompositeDisposable() }

    private val _usersLiveData = MutableLiveData<ViewState<List<User>>>()
    val usersLiveData: LiveData<ViewState<List<User>>>
        get() = _usersLiveData

    private var usersAlreadyLoaded = false

    var scrollPosition = 0
        private set

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    private suspend fun getCachedUsers() {
        val users = repository.getUsers().first()
        // If the users request has already completed, do not update the UI with the cached values.
        if (!usersAlreadyLoaded) {
            viewModelScope.launch(Dispatchers.Main) {
                _usersLiveData.value = ViewState.cache(users)
            }
        }
    }

    fun loadUsers() {
        usersAlreadyLoaded = false
        _usersLiveData.value = ViewState.loading()
        viewModelScope.launch(Dispatchers.IO) {
            // Get the cached users to update the ui with them
            getCachedUsers()
        }
        disposable.add(
            repository.requestUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ users ->
                    usersAlreadyLoaded = true
                    viewModelScope.launch(Dispatchers.IO) {
                        // Clear the table and save the new values
                        repository.deleteAllUsers()
                        repository.saveUsers(users)
                    }
                    // Update the users with the new values
                    _usersLiveData.value = ViewState.success(users)
                }, {
                    _usersLiveData.value = ViewState.error()
                })
        )
    }

    fun saveScrollPosition(position: Int) {
        scrollPosition = position
    }
}