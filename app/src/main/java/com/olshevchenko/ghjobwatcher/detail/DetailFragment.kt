package com.olshevchenko.ghjobwatcher.detail

//import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.olshevchenko.ghjobwatcher.databinding.FragmentDetailBinding

/**
 * The fragment shows the full vacation description
 */
class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val dataBinding = FragmentDetailBinding.inflate(inflater)
        dataBinding.lifecycleOwner = this

        val gitHubJob = DetailFragmentArgs.fromBundle(requireArguments()).selectedJob
        val viewModelFactory = DetailViewModelFactory(gitHubJob, application)
        dataBinding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(DetailViewModel::class.java)

        return dataBinding.root
    }
}