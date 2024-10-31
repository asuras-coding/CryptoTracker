package com.plcoding.cryptotracker.core.data.networking

import com.plcoding.cryptotracker.BuildConfig

fun constructUrl(url: String): String {
    return if (BuildConfig.BASE_URL in url) {
        url
    } else {
        BuildConfig.BASE_URL + url.removePrefix("/")
    }
}