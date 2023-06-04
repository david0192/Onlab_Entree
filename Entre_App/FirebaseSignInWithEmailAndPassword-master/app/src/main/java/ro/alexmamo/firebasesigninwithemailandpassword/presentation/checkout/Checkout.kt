package ro.alexmamo.firebasesigninwithemailandpassword.myapplication

import android.app.Activity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.razorpay.Checkout
import org.json.JSONObject
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.IntWrapper
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sportfacilities.TicketTypeViewModel

@Composable
fun CheckoutScreen(
    ticketTypeId: Int?,
    amount:Int?,
    navController: NavController,
    ttvm: TicketTypeViewModel,
    boughtTicketTypeId:IntWrapper
) {
    val amount = amount?.times(100)
    val orderId = "987439827"
    boughtTicketTypeId.value=ticketTypeId

    RazorpayPaymentScreen(
        amount = amount,
        orderId = orderId,
    )
}


@Composable
fun RazorpayPaymentScreen(
    amount: Int?,
    orderId: String,
) {
    val activity = LocalContext.current as Activity

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



        razorpay.open(activity, data)

        onDispose {

        }

    }
}