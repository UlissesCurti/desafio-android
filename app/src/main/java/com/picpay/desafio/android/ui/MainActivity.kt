package com.picpay.desafio.android.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.ViewState
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.di.MainModule
import com.picpay.desafio.android.extensions.ViewExtension.hide
import com.picpay.desafio.android.extensions.ViewExtension.show
import com.picpay.desafio.android.ui.adapter.UserListAdapter
import com.picpay.desafio.android.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private var adapter: UserListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).run {
            binding = this
            setContentView(root)

            loadKoinModules(modules)

            adapter = UserListAdapter()
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        observeViewModel()
        viewModel.loadUsers()
    }

    override fun onDestroy() {
        unloadKoinModules(modules)
        super.onDestroy()
    }

    private fun observeViewModel() {
        with(binding) {
            viewModel.usersLiveData.observe(this@MainActivity, {
                when (it.status) {
                    ViewState.Status.LOADING -> {
                        progressBar.show()
                    }
                    ViewState.Status.SUCCESS -> {
                        progressBar.hide()
                        if (it.data?.isNotEmpty() == true) {
                            adapter?.users = it.data
                        }
                    }
                    ViewState.Status.ERROR -> {
                        progressBar.hide()
                        recyclerView.hide()

                        val message = getString(R.string.error)
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    companion object {
        private val modules = listOf(MainModule.instance)
    }
}
