package com.vitorthemyth.composetutorials.permissions_handling

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    permission: PermissionTextProvider,
    isPermanentDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Permission Required")

        },
        text = {
            Text(
                text = permission.getDescription(isPermanentDeclined)
            )
        },
        modifier = modifier,

        confirmButton = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Divider()
                Text(
                    text = if (isPermanentDeclined) {
                        "Grant Permission"
                    } else {
                        "Okay"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentDeclined) {
                                onGoToSettings()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp)
                )
            }
        }
    )
}

interface PermissionTextProvider{
   fun getDescription(isPermanentDeclined: Boolean) : String
}

class CameraPermissionTextProvider : PermissionTextProvider{
    override fun getDescription(isPermanentDeclined: Boolean): String {
        return if (isPermanentDeclined){
            "It seems you permanently declined camera permission. " +
                    "You can go to app settings to grant it."
        }else{
            "This app needs access to your camera so that " +
                    "your friends can see you during a call"
        }
    }
}

class RecordAudioPermissionTextProvider : PermissionTextProvider{
    override fun getDescription(isPermanentDeclined: Boolean): String {
        return if (isPermanentDeclined){
            "It seems you permanently declined microphone permission. " +
                    "You can go to app settings to grant it."
        }else{
            "This app needs access to your microphone so that " +
                    "your friends can hear you during a call"
        }
    }
}

class PhoneCallPermissionTextProvider : PermissionTextProvider{
    override fun getDescription(isPermanentDeclined: Boolean): String {
        return if (isPermanentDeclined){
            "It seems you permanently declined phone calling permission. " +
                    "You can go to app settings to grant it."
        }else{
            "This app needs phone calling permission, so that you can make calls"
        }
    }
}