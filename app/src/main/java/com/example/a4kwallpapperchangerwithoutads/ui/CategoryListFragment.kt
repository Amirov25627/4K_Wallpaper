package com.example.a4kwallpapperchangerwithoutads.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.a4kwallpapperchangerwithoutads.R
import com.example.a4kwallpapperchangerwithoutads.databinding.CategoryListFragmentBinding
import com.example.a4kwallpapperchangerwithoutads.network.CategoriesData
import com.example.a4kwallpapperchangerwithoutads.viewmodel.ViewModel


class CategoryListFragment : Fragment() {
    private lateinit var binding: CategoryListFragmentBinding
    private val viewModel: ViewModel by activityViewModels()
    lateinit var adapter: CategoryListItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CategoryListFragmentBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewModel.hint.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.hint.visibility = View.VISIBLE
            }
        })


        binding.hintBackground.setOnClickListener() {
            binding.hint.visibility = View.GONE
            viewModel.hint.value = false
        }


        adapter = CategoryListItemAdapter(viewModel)
        binding.categoryRV.adapter = adapter

        //centering items on display
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.categoryRV)


        viewModel.getCategoryList()


//        viewModel.status.observe(viewLifecycleOwner, Observer {
//            updateStatus(it)
//        })
        viewModel.goToDetailsLV.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.setBackable()
                findNavController().navigate(R.id.action_categoryListFragment_to_picturesListFragment)
            }
        })

        viewModel.categoryList.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            showList(it)
        })
    }

    private fun showList(list: List<CategoriesData>) {
        adapter.setList(list)
    }


//    private fun updateStatus(status: ViewModel.STATUS) {
//        when (status) {
//            ViewModel.STATUS.LOADING -> {
//                binding.categoryRV.visibility = View.GONE
//                binding.statusIV.visibility = View.VISIBLE
//                binding.statusIV.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.loading_animation))
//            }
//            SharedViewModel.STATUS.SUCCESS -> {
//                binding.listRV.visibility = View.VISIBLE
//                binding.statusIV.visibility = View.GONE
//            }
//            SharedViewModel.STATUS.ERROR ->{
//                binding.listRV.visibility = View.GONE
//                binding.statusIV.visibility = View.VISIBLE
//                binding.statusIV.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_connection_error))
//            }
//        }
//    }
}