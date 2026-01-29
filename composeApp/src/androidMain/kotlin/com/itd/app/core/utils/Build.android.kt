package com.itd.app.core.utils

import android.os.Build

actual object Build {
    actual val FINGERPRINT: String
        get() = Build.FINGERPRINT
}