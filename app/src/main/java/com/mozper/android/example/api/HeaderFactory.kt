package com.mozper.android.example.api

import java.util.HashMap

class HeaderFactory {

    companion object{
        private var language: Pair<String, String> =
            Pair("lenguage", "en")
        private var contentType: Pair<String, String> =
            Pair("Content-Type", "application/json")


        fun getHeaders(): HashMap<String, String> {
            return hashMapOf(
                language, contentType
            )
        }
    }
}