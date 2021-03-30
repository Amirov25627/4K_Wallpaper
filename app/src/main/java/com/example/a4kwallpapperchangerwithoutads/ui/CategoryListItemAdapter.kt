package com.example.a4kwallpapperchangerwithoutads.ui

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4kwallpapperchangerwithoutads.databinding.CategoryListItemBinding
import com.example.a4kwallpapperchangerwithoutads.network.CategoriesData
import com.example.a4kwallpapperchangerwithoutads.viewmodel.ViewModel

class CategoryListItemAdapter(private val viewModel: ViewModel) : RecyclerView.Adapter<CategoryViewHolder>() {
    private val mList = mutableListOf<CategoriesData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryListItemBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(mList[position])
    }


    fun setList(list: List<CategoriesData>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }
}

class CategoryViewHolder(private val binding: CategoryListItemBinding, private val viewModel: ViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: CategoriesData) {
        val uri = Uri.parse(data.preview_photos.first().urls.small)
        Glide
                .with(binding.root.context)
                //.setPlaceHolder(R.drawable.loading_animation, R.drawable.ic_broken_image)
                .load(uri)
                .into(binding.image)

        binding.categoryName.text = data.title

        //click on item
        binding.root.setOnClickListener {
            viewModel.goToDetails(data)
        }
    }
}
