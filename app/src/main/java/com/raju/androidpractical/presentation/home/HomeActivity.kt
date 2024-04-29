// HomeActivity.kt

package com.raju.androidpractical.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.raju.androidpractical.R
import com.raju.androidpractical.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        setObserver()
    }

    private fun setObserver() {
        homeViewModel.isButtonEnabled.observe(this, Observer { isEnabled ->
            binding.btnNext.isEnabled = isEnabled ?: false
        })
    }

    private fun initView() {
        binding.edtPanCard.addTextChangedListener(textWatcher)
        binding.edtDay.addTextChangedListener(textWatcher)
        binding.edtMonth.addTextChangedListener(textWatcher)
        binding.edtYear.addTextChangedListener(textWatcher)
        binding.btnNext.setOnClickListener {
            onNextClick()
        }
        binding.tvDontHavePan.setOnClickListener {
            this.finish()
        }
    }

    private fun onNextClick() {
        if (homeViewModel.isButtonEnabled.value == true) {
            binding.edtPanCard.setText("")
            binding.edtDay.setText("")
            binding.edtMonth.setText("")
            binding.edtYear.setText("")
            Toast.makeText(
                this,
                getText(R.string.lbl_detail_submitted_successfully),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            homeViewModel.updatePan(binding.edtPanCard.text.toString())
            homeViewModel.updateDay(binding.edtDay.text.toString())
            homeViewModel.updateMonth(binding.edtMonth.text.toString())
            homeViewModel.updateYear(binding.edtYear.text.toString())
        }
    }
}
