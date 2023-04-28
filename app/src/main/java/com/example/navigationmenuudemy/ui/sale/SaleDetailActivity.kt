package com.example.navigationmenuudemy.ui.sale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.ActivitySaleDetailnBinding
import com.example.navigationmenuudemy.ui.extension.alertDialog
import com.example.navigationmenuudemy.ui.extension.isNull
import com.example.navigationmenuudemy.ui.extension.toast
import com.example.navigationmenuudemy.ui.extension.visibleIf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SaleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaleDetailnBinding
    private val viewModel: SaleDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaleDetailnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.txt_actionbar_title_sale)

        val saleDetailAdapter =
            SaleDetailAdapter(emptyList(), emptyList(), onQuantityModify = { modify, id ->
                viewModel.modifyQuantity(modify,id)
            })
        binding.rvSaleDescription.adapter = saleDetailAdapter
        viewModel.saleFound.observe(this) {
            if (it.isNull) {
                toast("No existe alguna compra en proceso!")
                buttonsOff()
                return@observe
            }
            binding.tvIdSale.text = "Venta #${it.id}"
        }
        viewModel.saleDescriptions.observe(this) {
            if (it.isNull) {
                toast("No existen items agregados!")
                return@observe
            }
            if(it.isEmpty()){
                toast("Ya no quedan productos en tu carrito de venta")
                buttonsOff()
            }
            saleDetailAdapter.listSaleDescriptions = it
            saleDetailAdapter.notifyDataSetChanged()
        }
        viewModel.saleProducts.observe(this) {
            binding.tvItemsCounter.text = "${it.size} items"
            saleDetailAdapter.listProducts = it
            saleDetailAdapter.notifyDataSetChanged()
        }
        viewModel.subTotal.observe(this) {
            binding.tvSubtotalSale.text = "S/. $it"
        }

        binding.btnCancelSale.setOnClickListener { cancelSale() }
        binding.btnRegisterSale.setOnClickListener { registerSale() }
    }

    private fun cancelSale() {
        alertDialog(actionNegative = {
            lifecycleScope.launch {
                if (viewModel.cancelSale()) {
                    finish()

                }
            }
        }, titleMessage = R.string.txt_alert_delete_sale)
    }

    private fun registerSale() {
        lifecycleScope.launch {
            if (viewModel.registerSale()) {
                finish()
            }
        }
    }

    private fun buttonsOff() {
        binding.btnRegisterSale.isEnabled = false
        binding.btnCancelSale.isEnabled = false
    }
}