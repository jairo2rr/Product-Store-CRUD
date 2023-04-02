package com.example.navigationmenuudemy.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.navigationmenuudemy.R
import com.example.navigationmenuudemy.databinding.ActivityMainBinding
import com.example.navigationmenuudemy.ui.extension.snackbarWithAction
import com.example.navigationmenuudemy.ui.extension.toast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermissions()
        val navController = findNavController(R.id.fragmentReplace)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,
            R.id.categoryFragment,
            R.id.profileFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun requestPermissions() {
        val permission = mutableListOf<String>()
        if (!checkExternalStoragePermission()) {
            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permission.isNotEmpty()) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this, permission.toTypedArray(), 0)
            }else{
                binding.root.snackbarWithAction(R.string.txt_snackbar_permission, textAction = R.string.txt_snackbar_go){
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", this.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }
            }
        }

    }

    private fun checkExternalStoragePermission() = ActivityCompat.checkSelfPermission(this,
        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    Log.d("PermissionsGranted", "${permissions[i]} is granted")
            }
        }

    }
}