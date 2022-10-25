package com.example.ncl.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ncl.R
import com.example.ncl.models.CruiseShip
import com.example.ncl.databinding.FragmentCruiseShipBinding
import com.example.ncl.utils.SHIP_BLISS
import com.example.ncl.utils.SHIP_ESCAPE
import com.example.ncl.utils.SHIP_SKY
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class CruiseShipFragment : Fragment() {

    private var _binding: FragmentCruiseShipBinding? = null
    private val binding: FragmentCruiseShipBinding get() = _binding!!
    private val cruiseShipViewModel by viewModels<CruiseShipViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCruiseShipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initializeView()
    }

    private fun initObservers() {
        cruiseShipViewModel.state.observe(viewLifecycleOwner) { state ->            when (state) {
            is State.Initial -> Unit
            is State.Loading -> {
                isDataVisible(false)
                handleLoading(true)
            }
            is State.Success -> {
                isDataVisible(true)
                handleLoading(false)
                handleSuccess(state.data)
            }
            is State.Failure -> {
                handleLoading(false)
                handleError()
            }
        }

        }
    }

    private fun initializeView() {
        with(binding) {
            btnSky.setOnClickListener { cruiseShipViewModel.getCruiseShips(SHIP_SKY) }
            btnBliss.setOnClickListener { cruiseShipViewModel.getCruiseShips(SHIP_BLISS) }
            btnEscape.setOnClickListener { cruiseShipViewModel.getCruiseShips(SHIP_ESCAPE) }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            progressBar.isVisible = isLoading
            tvError.isVisible = false
        }
    }

    private fun isDataVisible(isVisible: Boolean) {
        binding.group.isVisible = isVisible
    }

    private fun handleSuccess(cruiseShip: CruiseShip) = with(binding) {
        with(cruiseShip) {
            tvShipName.text = getString(R.string.ship_name).format(shipName)
            tvPassengerCapacity.text =
                getString(R.string.passenger_capacity).format(shipFacts.passengerCapacity)
            tvCrew.text = getString(R.string.crew).format(shipFacts.crew)
            tvInauguralDate.text =
                getString(R.string.inaugural_date).format(shipFacts.inauguralDate)
        }
    }
    private fun handleError() {
        binding.tvError.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}