package com.example.e_commerce.order_details_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.Home.viewmodel.HomeViewModelFactory
import com.example.e_commerce.HomeActivity
import com.example.e_commerce.databinding.FragmentOrderDetailsBinding
import com.example.e_commerce.model.pojo.order_response.Order
import com.example.e_commerce.model.repo.Repo
import com.example.e_commerce.orders.viewmodel.OrderViewModel
import com.example.e_commerce.services.db.ConcreteLocalSource
import com.example.e_commerce.services.network.ApiState
import com.example.e_commerce.services.network.ConcreteRemoteSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter
    private lateinit var factory: HomeViewModelFactory
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackToOrder.setOnClickListener {
            findNavController().popBackStack()
        }

        factory = HomeViewModelFactory(
            Repo.getInstance(
                ConcreteRemoteSource,
                ConcreteLocalSource.getInstance(requireContext())
            )
        )
        orderViewModel = ViewModelProvider(requireActivity(), factory)[OrderViewModel::class.java]

        orderDetailsAdapter = OrderDetailsAdapter(){
            TODO("navigate to product Details")
        }
        binding.rvOrdersDetails.apply {
            adapter = orderDetailsAdapter
            layoutManager = LinearLayoutManager(view.context).apply {
                orientation = RecyclerView.VERTICAL
            }
        }

        lifecycleScope.launch {
            orderViewModel.ordersStateFlow.collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        val order = it.data as Order
                        orderDetailsAdapter.submitList(order.line_items)
                        binding.apply {
                            tvOrderId.text = order.id.toString()
                            tvOrderDate.text = order.created_at
                            tvOrderPrice.text = order.total_price
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as HomeActivity).bottomNavigationBar.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as HomeActivity).bottomNavigationBar.visibility = View.VISIBLE
    }
}