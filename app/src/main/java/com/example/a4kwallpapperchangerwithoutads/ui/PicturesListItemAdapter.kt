package com.example.a4kwallpapperchangerwithoutads.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4kwallpapperchangerwithoutads.databinding.PictureListItemBinding
import com.example.a4kwallpapperchangerwithoutads.network.PicturesData
import com.example.a4kwallpapperchangerwithoutads.viewmodel.ViewModel

class PicturesListItemAdapter(private val viewModel: ViewModel):  RecyclerView.Adapter<PictureViewHolder>() {
    private val mList = mutableListOf<PicturesData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PictureListItemBinding.inflate(inflater, parent, false)
        return PictureViewHolder(binding, viewModel)
        }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(mList[position])
    }


    fun setList(list: List<PicturesData>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }
}

class PictureViewHolder(private val binding: PictureListItemBinding, private val viewModel: ViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: PicturesData) {
        val uri = Uri.parse(data.urls.regular)
        Glide
                .with(binding.root.context)
                .load(uri)
                .into(binding.pictureItemContainer)



        //click on item
        binding.root.setOnClickListener {
            viewModel.goToPicture(data)
        }
    }
}