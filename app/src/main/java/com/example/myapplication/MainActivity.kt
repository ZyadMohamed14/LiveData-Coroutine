package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        doIntialization()
        observeData()
    }

    private fun observeData() {
        viewModel.liveData.observe(this) {
            binding.LiveDataTV.text = it
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()

        }
        lifecycleScope.launch {
            viewModel.stateFlow.collectLatest {
                binding.stateflowtv.text = it
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
        lifecycleScope.launch {
            viewModel.sharedFlow.collectLatest {
                binding.sharedflowTv.text = it
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun doIntialization() {
        binding.LiveDataBTn.setOnClickListener {
            viewModel.setDataFromLiveData()

        }
        binding.stateflowBTN.setOnClickListener {
            viewModel.setDataFromStateFlow()
        }
        binding.flowBTN.setOnClickListener {
            lifecycleScope.launch {
                viewModel.setDataFromFlow().collectLatest {
                    binding.FlowTV.text = it.toString()
                    Snackbar.make(binding.root, it.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        binding.sharedflowBTN.setOnClickListener {
            lifecycleScope.launch { viewModel.setDataFromSharedStateFlow() }
        }

    }


}
