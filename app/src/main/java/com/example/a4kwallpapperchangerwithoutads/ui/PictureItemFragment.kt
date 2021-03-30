package com.example.a4kwallpapperchangerwithoutads.ui

import android.app.WallpaperManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.a4kwallpapperchangerwithoutads.MainActivity
import com.example.a4kwallpapperchangerwithoutads.R
import com.example.a4kwallpapperchangerwithoutads.databinding.PictureItemFragmentBinding
import com.example.a4kwallpapperchangerwithoutads.viewmodel.ViewModel

class PictureItemFragment : Fragment() {
    private lateinit var binding: PictureItemFragmentBinding
    private val viewModel: ViewModel by activityViewModels()
    private var timer = 0
    private var random = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PictureItemFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentPicture.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val uri = Uri.parse(it.urls.full)
                Glide
                        .with(binding.root.context)
                        //.setPlaceHolder(R.drawable.loading_animation, R.drawable.ic_broken_image)
                        .load(uri)
                        .into(binding.imageContainer)

            }
        })


        binding.setWallpaperButton.setOnClickListener {
            binding.settings.visibility = View.VISIBLE
            binding.setWallpaperButton.visibility = View.GONE
            binding.randomView.visibility = View.GONE
        }



            //checking time interval
            binding.timeInterval.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.min -> {timer = 15
                        binding.randomView.visibility = View.VISIBLE}
                    R.id.medium -> {timer = 30
                        binding.randomView.visibility = View.VISIBLE}
                    R.id.max -> {timer = 60
                        binding.randomView.visibility = View.VISIBLE}
                    R.id.never -> binding.randomView.visibility = View.GONE
                    }
            }

            //checking randomizer
            binding.randomizer.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    true -> random = true
                }
            }


        //on press SET WALLPAPER
        binding.setButton.setOnClickListener {
            viewModel.wallpaperSetter(timer, random, viewModel.currentPicture.value)
            Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show()
        }











    }

}