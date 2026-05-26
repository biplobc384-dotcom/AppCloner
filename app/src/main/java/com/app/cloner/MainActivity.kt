package com.app.cloner

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// এটিও পরিবর্তন করে অরিজিনাল পাথ দিন
import top.niunaijun.blackbox.BlackBoxCore

class MainActivity : AppCompatActivity() {

    // আমরা টেস্ট করার জন্য টেলিগ্রামের প্যাকেজ নাম ব্যবহার করছি
    private val targetPackage = "org.telegram.messenger"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnClone = findViewById<Button>(R.id.btnClone)
        val statusText = findViewById<TextView>(R.id.statusText)

        btnClone.setOnClickListener {
            try {
                statusText.text = "[ PROCESSING ]\nCHECKING VIRTUAL ENVIRONMENT..."

                // ১. চেক করা অ্যাপটি ইতিমধ্যে স্যান্ডবক্সে ইন্সটলড কি না
                if (!BlackBoxCore.get().isInstalled(targetPackage, 0)) {
                    statusText.text = "[ CLONING ]\nINSTALLING APP INTO SANDBOX..."

                    // ২. স্যান্ডবক্সে অ্যাপটি ক্লোন/ইন্সটল করা
                    val result = BlackBoxCore.get().installPackageAsUser(targetPackage, 0)

                    if (result.success) {
                        Toast.makeText(this, "Clone Successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        statusText.text = "[ ERROR ]\nCLONE FAILED!"
                        return@setOnClickListener
                    }
                }

                // ৩. ক্লোন করা অ্যাপটি ভার্চুয়াল এনভায়রনমেন্ট থেকে লঞ্চ করা
                statusText.text = "[ LAUNCHING ]\nSTARTING VIRTUAL PROCESS..."
                BlackBoxCore.get().launchApk(targetPackage, 0)

                statusText.text = "[ RUNNING ]\nCLONE IS ACTIVE"

            } catch (e: Exception) {
                e.printStackTrace()
                statusText.text = "[ CRASH ]\n${e.message}"
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}