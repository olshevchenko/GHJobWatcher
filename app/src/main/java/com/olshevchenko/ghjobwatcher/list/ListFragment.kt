package com.olshevchenko.ghjobwatcher.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.olshevchenko.ghjobwatcher.R
import com.olshevchenko.ghjobwatcher.databinding.FragmentListBinding
import kotlinx.android.synthetic.main.fragment_list.*

/** The fragment shows the list of the GitHub jobs */
class ListFragment : Fragment() {

    // Lazily initialize ListViewModel
    private val _viewModel: ListViewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the ListFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = FragmentListBinding.inflate(inflater)

        // allow Data Binding to Observe LiveData with the lifecycle of this Fragment
        dataBinding.lifecycleOwner = this

        // give the binding access to the ListViewModel
        dataBinding.viewModel = _viewModel

        // set the adapter of the jobsList RecyclerView with clickHandler lambda to tell
        // the viewModel ab't click on job item
        dataBinding.rvJobsList.adapter = JobListAdapter(JobClickListener {
            _viewModel.displayJobDetails(it)
        })

        // Observe the navigateToSelectedJob LiveData and Navigate when it isn't null
        // After navigating, call displayJobDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        _viewModel.navigateToSelectedJob.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(ListFragmentDirections.actionShowDetail(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                _viewModel.displayJobDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return dataBinding.root
    }

    /**
     * Support 'Swipe To Refresh' operation
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshLayout.setOnRefreshListener {
            _viewModel.reloadJobs()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_reload -> {
                _viewModel.reloadJobs()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
