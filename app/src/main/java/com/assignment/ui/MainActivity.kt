package com.assignment.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_main.*
import com.assignment.R
import com.assignment.data.model.UsersDto
import com.assignment.ui.home.HomeAdapter
import com.assignment.ui.home.HomeViewModel


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val viewModel: HomeViewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }
    val adapter : HomeAdapter by lazy { HomeAdapter(mutableListOf()) }
    private var searchDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar by lazy { toolbar_main_activity }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        with(viewModel) {
            usersData.observe(this@MainActivity, Observer {
                initView(it)
            })

            error.observe(this@MainActivity, Observer {
                progressBar_home.visibility = View.GONE
                Toast.makeText(this@MainActivity, "$it", Toast.LENGTH_LONG).show()
            })
            initSearchBar()
        }
    }

    private fun initView(it: UsersDto?) {
        rv_main_home.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        rv_main_home.adapter = adapter
        progressBar_home.visibility = View.GONE

        if (it != null && it.items.isNotEmpty()) {
            adapter.clear()
            adapter.add(it.items)

        } else {
            Toast.makeText(this@MainActivity, getString(R.string.empty_list), android.widget.Toast.LENGTH_LONG).show()
        }
    }

    fun initSearchBar() {
        search_view.setOnEditorActionListener({ v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_SEARCH) {
                searchQuery(v.getText().toString())
                hideSoftkeyboard()
                true
            } else
            false
        })
    }

    private fun hideSoftkeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search_view.getWindowToken(), 0)
    }

    private fun searchQuery(query: String) {

        clearSearchList()
        if (query.length > 0) {
            showLoader()
            viewModel.fetchUsers(query)
        }
    }

    private fun showLoader() {
        progressBar_home.visibility = View.VISIBLE
    }

    private fun clearSearchList() {
        adapter.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchDisposable?.dispose()
    }
}
