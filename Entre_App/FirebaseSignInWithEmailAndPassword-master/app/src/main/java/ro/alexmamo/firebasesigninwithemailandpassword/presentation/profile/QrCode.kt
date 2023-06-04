package ro.alexmamo.firebasesigninwithemailandpassword.myapplication

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QrCodeScreen(data: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val qrCodeBitmap = remember(data) { mutableStateOf<Bitmap?>(null) }

        LaunchedEffect(data) {
            qrCodeBitmap.value = generateQrCodeBitmap(data)
        }

        qrCodeBitmap.value?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "QR Code",
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

private fun generateQrCodeBitmap(data: String): Bitmap {
    val qrCodeSize = 512
    val qrCodeEncoder = QRCodeWriter()
    val bitMatrix = qrCodeEncoder.encode(data, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize)
    val bitmap = Bitmap.createBitmap(qrCodeSize, qrCodeSize, Bitmap.Config.RGB_565)

    for (x in 0 until qrCodeSize) {
        for (y in 0 until qrCodeSize) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
        }
    }

    return bitmap
}

