package com.pab.bfaa.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pab.bfaa.Activity.DetailActivity
import com.pab.bfaa.Adapter.ListGithubAdapter
import com.pab.bfaa.Data.Github
import com.pab.bfaa.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {
    var pbar : ProgressBar? = null
    var rv :RecyclerView? = null
     var list = ArrayList<Github>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         rv= view.findViewById(R.id.follower_rv) as RecyclerView
        rv!!.setHasFixedSize(true)
        pbar= view.findViewById(R.id.progressFollower) as ProgressBar
        pbar!!.visibility = View.GONE
        getUserDetail()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return  inflater.inflate(R.layout.fragment_follower, container, false)
    }
    private fun showRecyclerList() {
        this.rv!!.layoutManager = LinearLayoutManager(context)
        val listGithubAdapter= ListGithubAdapter(list)


        rv!!.adapter = listGithubAdapter

    }
    fun getUserDetail(){
    val context = activity as DetailActivity
        pbar!!.visibility = VISIBLE
        context.apiInterface?.getFollowers(context.github!!.username!!)?.enqueue(object : Callback<List<Github>> {

            override fun onResponse(
                call: Call<List<Github>>,
                response: Response<List<Github>>
            ) {
                if (response.isSuccessful) {

                    list = response.body() as ArrayList<Github>
                    pbar!!.visibility = View.GONE
                    showRecyclerList()
                }

            }

            override fun onFailure(call: Call<List<Github>>, error: Throwable) {
                Log.e("tag", "errornya ${error.message}")
            }
        })

    }

}