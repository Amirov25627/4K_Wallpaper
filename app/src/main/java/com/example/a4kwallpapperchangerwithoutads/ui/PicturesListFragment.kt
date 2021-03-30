package com.example.a4kwallpapperchangerwithoutads.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.a4kwallpapperchangerwithoutads.databinding.PicturesListFragmentBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.a4kwallpapperchangerwithoutads.R
import com.example.a4kwallpapperchangerwithoutads.network.CategoriesData
import com.example.a4kwallpapperchangerwithoutads.network.PicturesData
import com.example.a4kwallpapperchangerwithoutads.viewmodel.ViewModel


class PicturesListFragment : Fragment() {
    private lateinit var binding: PicturesListFragmentBinding
    private val viewModel: ViewModel by activityViewModels()
    lateinit var adapter: PicturesListItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PicturesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PicturesListItemAdapter(viewModel)
        binding.picturesRV.adapter = adapter

        viewModel.getPictureList()

        viewModel.goToPictureLV.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.setBackable()
                findNavController().navigate(R.id.action_picturesListFragment_to_pictureItemFragment)
            }
        })

        Log.d("CREATED", context.toString())
        viewModel.pictureList.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            showPicturesList(it)
        })
    }

    private fun showPicturesList(list: List<PicturesData>) {
        adapter.setList(list)
    }


}