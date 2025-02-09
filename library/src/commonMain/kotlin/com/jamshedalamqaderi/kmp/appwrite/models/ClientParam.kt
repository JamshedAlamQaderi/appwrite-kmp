package com.jamshedalamqaderi.kmp.appwrite.models

import kotlinx.io.files.Path

sealed class ClientParam {
    data class StringParam(val key: String, val value: String?) : ClientParam()

    data class MapParam(val key: String, val value: Map<String, String>) : ClientParam()

    data class ListParam(val key: String, val value: List<String>) : ClientParam()

    data class FileParam(val path: Path) : ClientParam()
}
