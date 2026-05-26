package com.app.cloner

import android.app.Application
import android.content.Context
// পুরনো black.app.core... মুছে নিচেরটি দিন
import top.niunaijun.blackbox.BlackBoxCore

class AppClonerApplication : Application() {
    // বাকি ভেতরের কোড সব একই থাকবে...
}

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        try {
            // ইঞ্জিন ইনিশিয়ালাইজেশন
            BlackBoxCore.get().doAttachBaseContext(base)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate() {
        super.onCreate()
        try {
            // ইঞ্জিন সাকসেসফুলি স্টার্ট করা
            BlackBoxCore.get().doCreate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}