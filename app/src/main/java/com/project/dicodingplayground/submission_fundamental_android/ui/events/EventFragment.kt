package com.project.dicodingplayground.submission_fundamental_android.ui.events

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.FragmentEventBinding
import com.project.dicodingplayground.submission_fundamental_android.data.Result
import com.project.dicodingplayground.submission_fundamental_android.data.local.entity.EventEntity

class EventFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabName: String
    private val viewModel: EventViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val eventAdapter = EventAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val destinationId = findNavController().currentDestination?.id
        when (destinationId) {
            R.id.upcoming -> {
                tabName = "UPCOMING"
                initView(
                    requireActivity().getString(R.string.upcoming_event),
                    requireActivity().getString(R.string.you_must_be_excited),
                )
            }

            R.id.finished -> {
                tabName = "FINISHED"
                initView(
                    requireActivity().getString(R.string.finished_event),
                    requireActivity().getString(R.string.you_have_missed_some_event),
                )
            }

            R.id.favorites -> {
                tabName = "FAVORITES"
                initView(
                    requireActivity().getString(R.string.favorites_event),
                    requireActivity().getString(R.string.recommendation_event_for_you),
                )
            }
        }
    }

    private fun initView(title: String, subTitle: String) {
        binding.textView.text = title
        binding.textView4.text = subTitle

        binding.rvEvent.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = eventAdapter
        }
        eventAdapter.setOnItemClickListener(object: EventAdapter.OnItemClickListener{
            override fun onClickEvent(event: EventEntity, adapterPosition: Int) {
                val intent = Intent(activity, EventDetailActivity::class.java)
                intent.putExtra(EventDetailActivity.EXTRA_DATA, event)
                startActivity(intent)
            }
        })

        if (tabName == "FAVORITES") {
            observeFavoriteEvent()
        } else {
            viewModel.getEvent(tabName).observe(viewLifecycleOwner) { event ->
                if (event != null) {
                    when (event) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val eventData = event.data
                            if (eventData.isNotEmpty()) {
                                binding.tvNoData.visibility = View.GONE
                                binding.rvEvent.visibility = View.VISIBLE
                                eventAdapter.submitList(eventData)
                            } else {
                                binding.tvNoData.visibility = View.VISIBLE
                                binding.rvEvent.visibility = View.GONE
                            }
                        }

                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "${activity?.getString(R.string.something_wrong)} ${event.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun observeFavoriteEvent() {
        viewModel.getFavoriteEvent().observe(viewLifecycleOwner) { favoriteEvent ->
            binding.progressBar.visibility = View.GONE
            if (favoriteEvent.isNotEmpty()) {
                binding.tvNoData.visibility = View.GONE
                binding.rvEvent.visibility = View.VISIBLE
                eventAdapter.submitList(favoriteEvent)
            } else {
                binding.tvNoData.visibility = View.VISIBLE
                binding.rvEvent.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}