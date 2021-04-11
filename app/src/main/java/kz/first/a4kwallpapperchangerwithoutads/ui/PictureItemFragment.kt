package kz.first.a4kwallpapperchangerwithoutads.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kz.first.a4kwallpapperchangerwithoutads.databinding.PictureItemFragmentBinding
import kz.first.a4kwallpapperchangerwithoutads.viewmodel.ViewModel


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


        //on press DOWNLOAD
        binding.justDownload.setOnClickListener {
            Toast.makeText(context, "Downloading wallpaper...", Toast.LENGTH_LONG).show()
            viewModel.wallpaperDownloader(timer, random, viewModel.currentPicture.value)
        }

        //on press SET WALLPAPER
        binding.setButton.setOnClickListener {
            Toast.makeText(context, "Setting wallpaper...", Toast.LENGTH_LONG).show()
            viewModel.wallpaperSetter(timer, random, viewModel.currentPicture.value)
        }











    }

}