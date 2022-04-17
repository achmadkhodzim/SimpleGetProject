package com.pab.bfaa.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pab.bfaa.Adapter.PagerAdapter
import com.pab.bfaa.Data.Github
import com.pab.bfaa.Rest.ApiInterface
import com.pab.bfaa.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    companion object {
        const val EXTRA_GITHUB = "extra_github"
    }
     var apiInterface: ApiInterface?=null
    var github : Github? = null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        github = intent.getParcelableExtra<Github>(EXTRA_GITHUB) as Github
        binding.viewpagerMain.adapter = PagerAdapter(supportFragmentManager)
        binding.tabsMain.setupWithViewPager(binding.viewpagerMain)

        supportActionBar!!.title = "User Profile"

        if(!github!!.followers.equals("")){
            bindData(github!!)
        }
        else{
            getUserDetail(github!!.username!!)
        }



    }
    fun bindData(github: Github){
        binding.tvName.text = github.name
         binding.tvUserName.text = "@"+github.username
        binding.tvFollowers.text = github.followers
        binding.tvFollowing.text = github.following
        binding.tvCompany.text = github.company
        binding.tvRepo.text = github.repository
        binding.tvLocation.text = github.location
        if ( URLUtil.isValidUrl(github.avatar)){
            Glide.with(this)
                .load(github.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(binding.avatar)
        }
        else{
            binding.avatar.setImageResource(github.avatar!!.toInt())
        }

        binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Akun ${github.name} keren banget loh")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
    fun getUserDetail(username: String){

        apiInterface?.getUser(username)?.enqueue(object : Callback<Github> {

            override fun onResponse(
                call: Call<Github>,
                response: Response<Github>
            ) {
                if (response.isSuccessful) {

                    val data = response.body() as Github
                    bindData(data)

                }

            }

            override fun onFailure(call: Call<Github>, error: Throwable) {
                Log.e("tag", "errornya ${error.message}")
            }
        })

    }
}