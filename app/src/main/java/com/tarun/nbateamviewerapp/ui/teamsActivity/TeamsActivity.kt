package com.tarun.nbateamviewerapp.ui.teamsActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tarun.nbateamviewerapp.databinding.ActivityTeamsBinding
import com.tarun.nbateamviewerapp.ui.viewModels.TeamsSharedViewModel
import com.tarun.nbateamviewerapp.ui.extensions.displayLongToast
import com.tarun.nbateamviewerapp.ui.extensions.setVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamsActivity : AppCompatActivity() {
    private val viewModel: TeamsSharedViewModel by viewModel()
    private lateinit var binding: ActivityTeamsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeLoadingState()
        observeErrorMessage()
    }

    /**
     * Observes the loading state and shows/hides the loader accordingly.
     */
    private fun observeLoadingState() {
        viewModel.isLoading.observe(this, {
            binding.teamsActivityProgress.setVisibility(it)
        })
    }

    /**
     * Observes and shows the error message as a Toast to the user.
     */
    private fun observeErrorMessage() {
        viewModel.errorMessage.observe(this, {
            displayLongToast(it)
        })
    }
}