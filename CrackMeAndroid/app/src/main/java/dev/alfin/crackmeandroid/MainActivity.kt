package dev.alfin.crackmeandroid

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.scottyab.rootbeer.RootBeer
import dev.alfin.crackmeandroid.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.jvm.java


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    override fun onStart() {
        super.onStart()

        if (AppState.isProVersion) {
            binding.tvStatus.text = getString(R.string.pro_version)
            binding.tvStatus.setTextColor(Color.GREEN)
            binding.btnEnterSerialNumber.visibility = View.INVISIBLE
        } else {
            binding.tvStatus.text = getString(R.string.trial_version)
            binding.tvStatus.setTextColor(Color.GRAY)
            binding.btnEnterSerialNumber.visibility = View.VISIBLE
        }
    }

    private fun setupUI() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnCheckRoot.setOnClickListener {
          onClickBtnCheckRoot()
        }

        binding.btnFetchCatFactsFromAPI.setOnClickListener {
           onClickBtnFetchCatFactsFromAPI()
        }

        binding.btnEnterSerialNumber.setOnClickListener {
            onClickBtnEnterSerialNumber()
        }
    }

    private fun onClickBtnCheckRoot() {
        val isRooted = isRooted()
        if (isRooted) {
            alert("ROOTED", "⚠️")
        } else {
            alert("Not Rooted", "✅")
        }
    }

    private fun onClickBtnFetchCatFactsFromAPI() {
        binding.progressBar.visibility = View.VISIBLE
        HttpClient.catService.getCatFacts().enqueue(object : Callback<CatResponse> {
            override fun onResponse(call: Call<CatResponse>, response: Response<CatResponse>) {
                if (response.isSuccessful) {
                    alert("✅ Success", "${response.body()?.data?.first() ?: ""} 😺")
                } else {
                    alert("❌ Failed", response.message())
                }
                binding.progressBar.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<CatResponse>, t: Throwable) {
                alert("❌ Failed", t.localizedMessage ?: "")
                binding.progressBar.visibility = View.INVISIBLE
            }
        })
    }

    private fun onClickBtnEnterSerialNumber() {
        val intent = Intent(this, VerifySerialNumberActivity::class.java)
        startActivity(intent)
    }

    private fun isRooted(): Boolean {
        val rootBeer = RootBeer(this)
        return rootBeer.isRooted
    }
 }

class AppState {
    companion object {
        var isProVersion = false
    }
}

fun AppCompatActivity.alert(title: String, message: String) {
    val alertDialog = AlertDialog.Builder(this)

    alertDialog.apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton("OK") { _, _ -> }
    }.create().show()
}