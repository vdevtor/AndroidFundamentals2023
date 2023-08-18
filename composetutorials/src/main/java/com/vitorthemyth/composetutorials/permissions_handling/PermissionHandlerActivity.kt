package com.vitorthemyth.composetutorials.permissions_handling

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vitorthemyth.composetutorials.permissions_handling.ui.theme.AndroidFundamentals2023Theme

class PermissionHandlerActivity : ComponentActivity() {

    private val permissionsToRequest = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE,
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidFundamentals2023Theme {
                val viewModel = viewModel<PermissionsViewModel>()
                val dialogQueue = viewModel.visiblePermissionDialogQueue
                
                val cameraPermissionLauncher = rememberLauncherForActivityResult(
                    contract =  ActivityResultContracts.RequestPermission(),
                    onResult = {isGranted->
                        viewModel.onPermissionResult(
                            permission = Manifest.permission.CAMERA,
                            isGranted = isGranted
                        )
                    }
                )

                val multiplePermissionLauncher = rememberLauncherForActivityResult(
                    contract =  ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = {permsMap->
                        permissionsToRequest.forEach { permission->
                            viewModel.onPermissionResult(
                                permission = permission,
                                isGranted = permsMap[permission] == true
                            )
                        }
                    }
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }) {
                        Text(text = "Request one permission")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        multiplePermissionLauncher.launch(
                            arrayOf(Manifest.permission.CALL_PHONE,Manifest.permission.RECORD_AUDIO)
                        )
                    }) {
                        Text(text = "Request multiple permission")
                    }
                }
                dialogQueue.reversed().forEach {permission ->
                    PermissionDialog(
                        permission = when (permission){
                            Manifest.permission.CAMERA ->{
                                CameraPermissionTextProvider()
                            }
                            Manifest.permission.RECORD_AUDIO ->{
                                RecordAudioPermissionTextProvider()
                            }
                            Manifest.permission.CALL_PHONE ->{
                                PhoneCallPermissionTextProvider()
                            }
                            else -> return@forEach
                        },
                        isPermanentDeclined = !shouldShowRequestPermissionRationale(permission),
                        onDismiss = viewModel::dismissDialog,
                        onOkClick = {
                                    viewModel.dismissDialog()
                                multiplePermissionLauncher.launch(
                                    arrayOf(permission)
                                )
                        },
                        onGoToSettings = {
                            this.openAppSettings()
                        }
                    )
                    
                }
            }
        }
    }
}

fun Activity.openAppSettings(){
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package",packageName,null)
    ).also(::startActivity)
}
