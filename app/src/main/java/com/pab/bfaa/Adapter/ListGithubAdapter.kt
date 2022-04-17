package com.pab.bfaa.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pab.bfaa.Activity.DetailActivity
import com.pab.bfaa.Activity.MainActivity
import com.pab.bfaa.Data.Github
import com.pab.bfaa.databinding.ItemRowGithubBinding

class ListGithubAdapter(private val listGithub: ArrayList<Github>)  : RecyclerView.Adapter<ListGithubAdapter.ListViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listGithub[position])
    }

    override fun getItemCount(): Int = listGithub.size
    fun clear() {
        listGithub.clear()
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemRowGithubBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(github: Github) {
            with(binding){
                if(github.name.isNullOrEmpty()){
                    tvItemName.text = github.username
                }
                else{

                    tvItemName.text = github.name
                }

                tvItemDetail.text = github.location
                if ( URLUtil.isValidUrl(github.avatar)){
                    Glide.with(itemView.context)
                        .load(github.avatar)
                        .apply(RequestOptions().override(55, 55))
                        .into(imgItemPhoto)
                }
                else{
                    imgItemPhoto.setImageResource(github.avatar!!.toInt())
                }



                itemView.setOnClickListener {
                    val activity = itemView.context as Activity
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_GITHUB, github)
                    activity.startActivity(intent)

                }

            }
        }
    }

}