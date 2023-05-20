package ro.alexmamo.firebasesigninwithemailandpassword.myapplication

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.Checkout
import org.json.JSONObject

@Composable
fun CheckoutScreen(
    ticketTypeId: Int?,
    amount:Int?,
    navController: NavController,
    ttvm:TicketTypeViewModel
) {
    val amount = amount?.times(100)
    val orderId = "987439827"

    RazorpayPaymentScreen(
        amount = amount,
        orderId = orderId,
        onPaymentSuccess = {
            ttvm.AddTicketToUser(ticketTypeId, email= FirebaseAuth.getInstance().currentUser?.email)
            navController.navigate(route="home_sportfacilities")
        },
        onPaymentError = {
            navController.navigate(route="home_sportfacilities")
        }
    )
}


@Composable
fun RazorpayPaymentScreen(
    amount: Int?,
    orderId: String,
    onPaymentSuccess: () -> Unit,
    onPaymentError: () -> Unit
) {
    val activity = LocalContext.current as Activity

    var paymentResponseReceived by remember { mutableStateOf(false) }
    val receiver =remember { object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Handle the received intent, e.g., check for payment success or error
            // and call the corresponding callback functions
            val paymentId = intent?.getStringExtra("payment_id")
            val error = intent?.getStringExtra("error_description")

            if (paymentId != null) {
                onPaymentSuccess()

            } else if (error != null) {
                onPaymentError()

            }


            // Finish the activity
            paymentResponseReceived = true
        }

        }
    }

    DisposableEffect(Unit) {
        val razorpay = Checkout()
        razorpay.setKeyID("rzp_test_72ElRpCUZSRQ6O")

        val data = JSONObject()
        data.put("amount", amount)
        data.put("name", "My Store")
        data.put("description", "Payment for Order #$orderId")
        //data.put("order_id", orderId)
        data.put("currency", "HUF")
        data.put("prefill.contact", "9999999999")
        data.put("prefill.email", "test@test.com")

        val filter = IntentFilter()
        filter.addAction("com.razorpay.payment")
        activity.registerReceiver(receiver, filter)

        razorpay.open(activity, data)

        if (paymentResponseReceived) {
            activity.unregisterReceiver(receiver)
            activity.finish()
        }

        onDispose {
            // Unregister the receiver if the effect is disposed
            activity.unregisterReceiver(receiver)
            activity.finish()
        }

    }
}
