package com.example.navigationmenuudemy.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentHomeBinding
import com.example.navigationmenuudemy.ui.category.DialogCategoryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddProduct.setOnClickListener {
            openDialogProductFragment()
        }
    }

    private fun openDialogProductFragment() {

        val dialog = DialogProductFragment(){
            println("Hola")
        }

        dialog.show(parentFragmentManager, "showDialogProduct")
    }


}