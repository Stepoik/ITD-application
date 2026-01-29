package com.itd.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.itd.app.core.utils.createDatastore
import com.itd.app.core.utils.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { createAndroidDataStore(get()) }
}

fun createAndroidDataStore(context: Context): DataStore<Preferences> {
    return createDatastore { context.filesDir.resolve(dataStoreFileName).absolutePath }
}