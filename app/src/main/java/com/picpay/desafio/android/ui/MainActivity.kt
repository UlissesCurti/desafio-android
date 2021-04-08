package com.picpay.desafio.android.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.ViewState
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.di.MainModule
import com.picpay.desafio.android.extensions.ViewExtension.hide
import com.picpay.desafio.android.extensions.ViewExtension.show
import com.picpay.desafio.android.ui.adapter.UserListAdapter
import com.picpay.desafio.android.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private var usersAdapter: UserListAdapter? = null

    private var lastScrollPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).run {
            binding = this
            setContentView(root)
        }

        loadKoinModules(modules)

        initViews()
        observeViewModel()
        viewModel.loadUsers()
    }

    override fun onPause() {
        viewModel.saveScrollPosition(lastScrollPosition)
        super.onPause()
    }

    override fun onDestroy() {
        unloadKoinModules(modules)
        super.onDestroy()
    }

    private fun initViews() {
        with(binding) {
            nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                lastScrollPosition = scrollY
            })

            usersAdapter = UserListAdapter()
            recyclerView.run {
                adapter = usersAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    /**
     * TODO:
     * If the diff after rotating screen and reloading the data is different, do not auto scroll.
     * If the diff is equals, them auto scroll
     **/

    private fun observeViewModel() {
        with(binding) {

            viewModel.usersLiveData.observe(this@MainActivity, {
                when (it.status) {
                    ViewState.Status.LOADING -> {
                        progressBar.show()
                    }
                    ViewState.Status.CACHE -> {
                        loadUsersIntoAdapter(it.data)
                    }
                    ViewState.Status.SUCCESS -> {
                        progressBar.hide()
                        loadUsersIntoAdapter(it.data)

                    }
                    ViewState.Status.ERROR -> {
                        progressBar.hide()
                        val message = getString(R.string.error)
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun loadUsersIntoAdapter(data: List<User>?) {
        if (data?.isNotEmpty() == true) {
            usersAdapter?.users = data
            recyclerView.post {
                binding.nestedScroll.scrollTo(0, viewModel.scrollPosition)
            }
        }
    }

    companion object {
        private val modules = listOf(MainModule.instance)
    }
}
