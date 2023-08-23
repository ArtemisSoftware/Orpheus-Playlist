package com.artemissoftware.orpheusplaylist.utils.extensions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.isPermissionGranted(name: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        name,
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.isPermissionGranted(permissions: Array<String>): Boolean = permissions.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}
