package com.example.fundo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fundo.R
import com.example.fundo.databinding.FragmentArchiveBinding
import com.example.fundo.databinding.FragmentNotesBinding

class ArchiveFragment : Fragment() {

    lateinit var binding : FragmentArchiveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArchiveBinding.inflate(layoutInflater)





        return binding.root
    }
}