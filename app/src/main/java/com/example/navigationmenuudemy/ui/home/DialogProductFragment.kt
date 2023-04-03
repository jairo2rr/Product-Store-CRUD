package com.example.navigationmenuudemy.ui.home

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.FragmentDialogProductBinding
import com.example.navigationmenuudemy.domain.model.Product
import com.example.navigationmenuudemy.ui.extension.*
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DialogProductFragment(
    private val onCreateProduct: (Boolean) -> Unit,
    private val product: Product? = null,
) : DialogFragment() {
    private lateinit var binding: FragmentDialogProductBinding
    private val viewModel: DialogProductViewModel by viewModels()
    private val listCheck = mutableListOf<Boolean>(false, false, false, false)
    private var lastItemSelected: String? = null
    private var uri: String = ""
    private var pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.imgProduct.setImageURI(uri)
            this.uri = uri.toString()
        } else {
            toast("No se selecciono ninguna imagen.")
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
                pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(requireActivity(),
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        0)
                } else {
                    binding.root.snackbarWithAction(R.string.txt_snackbar_permission,
                        textAction = R.string.txt_snackbar_go) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", requireActivity().packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                }

            }
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogProductBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        binding.btnSelectImage.setOnClickListener {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
        }
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnAdd.setOnClickListener {
            if (checkErrorFields()) {
                return@setOnClickListener
            }
            intentInsertProduct()
        }
        binding.atvSelectCategory.setOnItemClickListener { parent, _, position, _ ->
            lastItemSelected = parent.getItemAtPosition(position).toString()
            if (listCheck[0]) {
                binding.layoutProductCategory.error = null
                binding.layoutProductCategory.isErrorEnabled = false
            }
        }
        viewModel.isLoading.observe(this) {
            turnVisibilityElements(!it)
            binding.pbLoading visibleIf it
        }
        viewModel.listCategories.observe(this) { list ->
            (binding.layoutProductCategory.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
                list.map { it.name }.toTypedArray())
            if (product != null && list.isNotEmpty()) {
                val item = list.find { it.id == product.categoryId }
                binding.atvSelectCategory.setText(item!!.name, true)
                lastItemSelected = item.name
            }
        }
        viewModel.isCreated.observe(this) {
            if (it) {
                onCreateProduct(it)
                dismiss()
            }
        }
        return builder.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel.loadCategories()
        setInfoProduct()
        setEventForFields()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun intentInsertProduct() {
        val name = binding.etProductName.text.toString()
        val price = binding.etProductPrice.text.toString().toFloat()
        val stock = binding.etProductStock.text.toString().toInt()
        if (product != null) {
            viewModel.editProduct(product.copy(
                product = name,
                price = price,
                stock = stock
            ),lastItemSelected!!)
            return
        }
        viewModel.createProduct(lastItemSelected!!, name, price, stock, uri)
    }

//    private fun intentCreateProduct(): Boolean {
//        val name = binding.etProductName.text.toString()
//        val price = binding.etProductPrice.text.toString().toFloat()
//        val stock = binding.etProductStock.text.toString().toInt()
//        return viewModel.createProduct(lastItemSelected!!, name, price, stock, uri)
//    }

    private fun turnVisibilityElements(toShow: Boolean) {
        binding.layoutProductCategory.visibility = if (toShow) View.VISIBLE else View.GONE
        binding.layoutProductName.visibility = if (toShow) View.VISIBLE else View.GONE
        binding.layoutProductPrice.visibility = if (toShow) View.VISIBLE else View.GONE
        binding.layoutProductStock.visibility = if (toShow) View.VISIBLE else View.GONE
        binding.btnAdd.isEnabled = toShow
        binding.btnCancel.isEnabled = toShow
    }

    private fun setEventForFields() {
        with(binding) {
            etProductName.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && listCheck[1]) {
                    layoutProductName.error = null
                    layoutProductName.isErrorEnabled = false
                }
            }
            etProductPrice.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && listCheck[2]) {
                    layoutProductPrice.error = null
                    layoutProductPrice.isErrorEnabled = false
                }
            }
            etProductStock.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrEmpty() && listCheck[3]) {
                    layoutProductStock.error = null
                    layoutProductStock.isErrorEnabled = false
                }
            }
        }
    }

    private fun checkErrorFields(): Boolean {
        val messageError = getString(R.string.txt_field_empty_error)
        listCheck.clear()
        with(binding) {
            val isTheSame = atvSelectCategory.text.toString().trim() == lastItemSelected
            val atCondition = lastItemSelected != null && isTheSame
            if (!isTheSame)
                atvSelectCategory.setText("")
            if (!atCondition)
                layoutProductCategory.error = messageError
            if (etProductName.isEmpty)
                layoutProductName.error = messageError
            if (etProductPrice.isEmpty)
                layoutProductPrice.error = messageError
            if (etProductStock.isEmpty)
                layoutProductStock.error = messageError
            listCheck.addAll(arrayOf(!atCondition,
                etProductName.isEmpty,
                etProductPrice.isEmpty,
                etProductStock.isEmpty))
        }
        return listCheck.contains(true)
    }

    private fun setInfoProduct() {
        if (product != null) {
            binding.etProductPrice.setText("${product.price}")
            binding.etProductStock.setText("${product.stock}")
            binding.etProductName.setText(product.product)
            binding.btnAdd.setText(R.string.txt_btn_update)
        }
    }

}