package com.pab.bfaa.Activity

import ApiClient
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.pab.bfaa.Adapter.ListGithubAdapter
import com.pab.bfaa.Data.Github
import com.pab.bfaa.Data.GithubSearchResponse
import com.pab.bfaa.R
import com.pab.bfaa.Rest.ApiInterface
import com.pab.bfaa.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Github>()
    private var apiInterface: ApiInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Github User"
        binding.rvGithub.setHasFixedSize(true)
        binding.progressBar.visibility = GONE
        list.addAll(getListGithub())
        showRecyclerList()


    }
    fun searchUser(query: String){
        binding.progressBar.visibility = VISIBLE
        val listGithub = ArrayList<Github>()
        apiInterface?.searchUser(query)?.enqueue(object : Callback<GithubSearchResponse> {

            override fun onResponse(
                call: Call<GithubSearchResponse>,
                response: Response<GithubSearchResponse>
            ) {
                if (response.isSuccessful) {
                    list.clear()
                    val data = response.body()
                    Log.d("tag", "responsennya ${data?.total}")
                    Log.d("data", "responsennya ${data?.listGithub?.get(1)?.avatar}")

                    data?.listGithub?.indices!!.forEach { position ->
                        val github = Github(
                            data.listGithub[position].username,
                            data.listGithub[position].username,
                            "",
                            "",
                            "",
                            "",
                            "",
                            data.listGithub[position].avatar
                        )
                        listGithub.add(github)
                    }
                }
                list.addAll(listGithub)
                binding.progressBar.visibility = GONE
                showRecyclerList()
            }

            override fun onFailure(call: Call<GithubSearchResponse>, error: Throwable) {
                Log.e("tag", "errornya ${error.message}")
            }
        })

    }
    @SuppressLint("Recycle")
    fun getListGithub(): ArrayList<Github> {
        val dataName = resources.getStringArray(R.array.name)
        val dataUserName = resources.getStringArray(R.array.username)
        val dataCompany = resources.getStringArray(R.array.company)
        val dataPhoto = resources.obtainTypedArray(R.array.avatar)
        val dataFollowers = resources.getStringArray(R.array.followers)
        val dataFollowing = resources.getStringArray(R.array.following)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataRepository = resources.getStringArray(R.array.repository)
        val listGithub = ArrayList<Github>()
        for (position in dataName.indices) {
            val github = Github(
                dataName[position],
                dataUserName[position],
                dataFollowers[position],
                dataFollowing[position],
                dataCompany[position],
                dataRepository[position],
                dataLocation[position],
                dataPhoto.getResourceId(position, -1).toString()
            )
            listGithub.add(github)
        }
        return listGithub
    }

    private fun showRecyclerList() {
        binding.rvGithub.layoutManager = LinearLayoutManager(this)
        val listGithubAdapter= ListGithubAdapter(list)


        binding.rvGithub.adapter = listGithubAdapter

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        val searchViewItem = menu.findItem(R.id.search)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchViewItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                // Do something when collapsed
                Log.i(TAG, "onMenuItemActionCollapse " + item.itemId)
                list.clear()
                list.addAll(getListGithub())
                showRecyclerList()
                return true // Return true to collapse action view
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {

                Log.i(TAG, "onMenuItemActionExpand " + item.itemId)
                return true
            }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                searchUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menu2 -> {

                true
            }
            else -> true
        }
    }
}