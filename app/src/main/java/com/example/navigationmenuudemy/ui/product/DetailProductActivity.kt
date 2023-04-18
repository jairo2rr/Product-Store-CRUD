package com.example.navigationmenuudemy.ui.product

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.ActivityDetailProductBinding
import com.example.navigationmenuudemy.domain.model.Product
import com.example.navigationmenuudemy.ui.home.DialogProductFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductActivity: AppCompatActivity() {
    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"
        const val REQUEST_CODE = 1
    }

    private val viewModel: DetailProductViewModel by viewModels()
    private lateinit var binding: ActivityDetailProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra(PRODUCT_ID, 0)
        supportActionBar?.title = getString(R.string.txt_actionbar_title_detail)
        viewModel.findProductFromId(id)
        viewModel.loading.observe(this) {
            binding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.product.observe(this) { product ->
            with(binding) {
                tvInfoProduct.text = "${product.id} - ${product.product}"

                tvProductDescription.text =
                    StringBuilder().appendLine("${getString(R.string.txt_field_category)}: ${product.categoryId}\n")
                        .appendLine("${getString(R.string.txt_field_price)}: ${product.price}\n").append("${getString(R.string.txt_field_stock)}: ${product.stock}")

                imgEditProduct.setOnClickListener {
                    openDialogProductFragment(product)
                }

                imgDeleteProduct.setOnClickListener {
                    AlertDialog.Builder(this@DetailProductActivity)
                        .setTitle(R.string.txt_alert_delete_product)
                        .setNegativeButton(R.string.txt_btn_delete) { dialog, number ->
                            deleteProduct()
                        }.setPositiveButton(R.string.txt_btn_cancel) { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }

                fabFavoriteProduct.setOnClickListener {
                    viewModel.setFavorite()
                }
                if(product.favorite){
                    binding.fabFavoriteProduct.setImageResource(R.drawable.ic_favorite)
                }else{
                    binding.fabFavoriteProduct.setImageResource(R.drawable.ic_favorite_border)
                }
                btnAddToCart.setOnClickListener {
                    continueAddSale(product)
                }
            }
            //initOpenDocumentLauncher(product.uri)
        }
//        viewModel.favorite.observe(this) {
//            if (it) {
//                binding.fabFavoriteProduct.setImageResource(R.drawable.ic_favorite)
//                return@observe
//            }
//            binding.fabFavoriteProduct.setImageResource(R.drawable.ic_favorite_border)
//        }
    }

    private fun deleteProduct() {
        viewModel.deleteProduct()
        //onDelete()
        finish()
    }

    private fun initOpenDocumentLauncher(txtUri: String) {
        val uri = Uri.parse(txtUri)
        binding.imageView.setImageURI(uri)
    }

    private fun openDialogProductFragment(product: Product) {

        val dialog =
            DialogProductFragment(product = product, onCreateProduct = {
                canUpdate()
            })
        dialog.show(supportFragmentManager, "DialogProduct")
    }

    private fun canUpdate() {
        viewModel.updateInfoProduct()
    }

    private fun continueAddSale(product: Product) {
        val dialog =
            DialogAddSaleFragment(product=product) {
                canUpdate()
            }
        dialog.show(supportFragmentManager, "showDialogProduct")
    }
}