package dev.alfin.crackmeandroid

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.alfin.crackmeandroid.databinding.ActivityVerifySerialNumberBinding

class VerifySerialNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifySerialNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding = ActivityVerifySerialNumberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnVerify.setOnClickListener {
            onClickBtnVerify()
        }
    }

    private fun onClickBtnVerify() {
        val isValid = verifySerialNumber(serial = binding.textField.text.toString())
        if (isValid) {
            AppState.isProVersion = true
            Toast.makeText(this, "✅ Success!", Toast.LENGTH_LONG).show()
            finish()
        } else {
            alert("❌ Failed", "Invalid Serial Number")
        }
    }

    private fun verifySerialNumber(serial: String): Boolean {
        val validSerialNumber = hexToString("3132332D313233")
        val isValid = serial == validSerialNumber
        return isValid
    }

}

fun hexToString(hex: String): String {
    val len = hex.length
    val data = ByteArray(len / 2)
    var i = 0
    while (i < len) {
        data[i / 2] = ((Character.digit(hex[i], 16) shl 4) + Character.digit(hex[i + 1], 16)).toByte()
        i += 2
    }
    return String(data, Charsets.UTF_8)
}
