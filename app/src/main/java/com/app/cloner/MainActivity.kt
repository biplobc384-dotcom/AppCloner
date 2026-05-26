package com.app.cloner

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import top.niunaijun.blackbox.BlackBoxCore

class MainActivity : AppCompatActivity() {

    // নিশ্চিত করুন আপনার মেইন ফোনে টেলিগ্রাম আগে থেকেই ইন্সটল করা আছে
    private val targetPackage = "org.telegram.messenger"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnClone = findViewById<Button>(R.id.btnClone)
        val statusText = findViewById<TextView>(R.id.statusText)

        btnClone.setOnClickListener {
            try {
                statusText.text = "[ PROCESSING ]\nCHECKING VIRTUAL ENVIRONMENT..."

                // ডিফল্ট ইউজার আইডি হিসেবে 0 সেট করা হলো
                val userId = 0

                // ১. চেক করা অ্যাপটি ইতিমধ্যে স্যান্ডবক্সে ইন্সটলড কি না
                if (!BlackBoxCore.get().isInstalled(targetPackage, userId)) {
                    statusText.text = "[ CLONING ]\nINSTALLING APP INTO SANDBOX..."

                    // ২. স্যান্ডবক্সে অ্যাপটি ক্লোন/ইন্সটল করা
                    // এখানে installPackageAsUser এর বদলে installPackage ব্যবহার করা হয়েছে যা আপনার ইঞ্জিনে আছে
                    val result = BlackBoxCore.get().installPackage(targetPackage, userId)

                    if (result.success) {
                        Toast.makeText(this, "Clone Successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        statusText.text = "[ ERROR ]\nCLONE FAILED!\nREASON: ${result.msg}"
                        return@setOnClickListener
                    }
                }

                // ৩. ক্লোন করা অ্যাপটি ভার্চুয়াল এনভায়রনমেন্ট থেকে লঞ্চ করা
                statusText.text = "[ LAUNCHING ]\nSTARTING VIRTUAL PROCESS..."
                BlackBoxCore.get().launchApk(targetPackage, userId)

                statusText.text = "[ RUNNING ]\nCLONE IS ACTIVE"

            } catch (e: Exception) {
                e.printStackTrace()
                statusText.text = "[ CRASH ]\n${e.message}"
            }
        }
    }
}