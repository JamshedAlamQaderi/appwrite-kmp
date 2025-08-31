package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.enums.ImageFormat
import com.jamshedalamqaderi.kmp.appwrite.enums.ImageGravity
import io.ktor.client.call.*
import com.jamshedalamqaderi.kmp.appwrite.models.File
import com.jamshedalamqaderi.kmp.appwrite.models.FileList
import com.jamshedalamqaderi.kmp.appwrite.models.Progress
import io.ktor.http.HttpMethod
import kotlinx.io.files.Path
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.json.JsonElement
import kotlin.jvm.JvmOverloads

/**
 * The Storage service allows you to manage your project files.
 **/
class Storage(client: Client) : Service(client) {
    /**
     * List files
     *
     * Get a list of all the user files. You can use the query params to filter your results.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long. You may filter on the following attributes: name, signature, mimeType, sizeOriginal, chunksTotal, chunksUploaded
     * @param search Search term to filter your list results. Max length: 256 chars.
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.FileList]
     */
    @JvmOverloads
    suspend fun listFiles(
        bucketId: String,
        queries: List<String>? = null,
        search: String? = null,
    ): FileList {
        val apiPath =
            "/storage/buckets/{bucketId}/files"
                .replace("{bucketId}", bucketId)

        val apiParams =
            listOf(
                ClientParam.ListParam("queries", queries ?: emptyList()),
                ClientParam.StringParam("search", search),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            FileList.serializer(),
            apiHeaders,
            apiParams,
        )
    }

    /**
     * Create file
     *
     * Create a new file. Before using this route, you should create a new bucket resource using either a [server integration](https://appwrite.io/docs/server/storage#storageCreateBucket) API or directly from your Appwrite console.Larger files should be uploaded using multiple requests with the [content-range](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Range) header to send a partial request with a maximum supported chunk of `5MB`. The `content-range` header values should always be in bytes.When the first request is sent, the server will return the **File** object, and the subsequent part request must include the file&#039;s **id** in `x-appwrite-id` header to allow the server to know that the partial upload is for the existing file and not for a new one.If you&#039;re creating a new file using one of the Appwrite SDKs, all the chunking logic will be managed by the SDK internally.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File ID. Choose a custom ID or generate a random ID with `ID.unique()`. Valid chars are a-z, A-Z, 0-9, period, hyphen, and underscore. Can't start with a special char. Max length is 36 chars.
     * @param file Binary file. Appwrite SDKs provide helpers to handle file input. [Learn about file input](https://appwrite.io/docs/products/storage/upload-download#input-file).
     * @param permissions An array of permission strings. By default, only the current user is granted all permissions. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.File]
     */
    @JvmOverloads
    suspend fun createFile(
        bucketId: String,
        fileId: String,
        file: Path,
        permissions: List<String>? = null,
        onProgress: ((Progress) -> Unit)? = null,
    ): File {
        val apiPath =
            "/storage/buckets/{bucketId}/files"
                .replace("{bucketId}", bucketId)

        val apiParams =
            listOf(
                ClientParam.StringParam("fileId", fileId),
                ClientParam.FileParam(file),
                ClientParam.ListParam("permissions", permissions ?: emptyList()),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "multipart/form-data",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            File.serializer(),
            apiHeaders,
            apiParams,
            onUpload = onProgress,
        )
    }

    /**
     * Get file
     *
     * Get a file by its unique ID. This endpoint response returns a JSON object with the file metadata.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File ID.
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.File]
     */
    suspend fun getFile(
        bucketId: String,
        fileId: String,
    ): File {
        val apiPath =
            "/storage/buckets/{bucketId}/files/{fileId}"
                .replace("{bucketId}", bucketId)
                .replace("{fileId}", fileId)

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            File.serializer(),
            apiHeaders,
        )
    }

    /**
     * Update file
     *
     * Update a file by its unique ID. Only users with write permissions have access to update this resource.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File unique ID.
     * @param name Name of the file
     * @param permissions An array of permission string. By default, the current permissions are inherited. [Learn more about permissions](https://appwrite.io/docs/permissions).
     * @return [com.jamshedalamqaderi.appwrite.kmp.models.File]
     */
    @JvmOverloads
    suspend fun updateFile(
        bucketId: String,
        fileId: String,
        name: String? = null,
        permissions: List<String>? = null,
    ): File {
        val apiPath =
            "/storage/buckets/{bucketId}/files/{fileId}"
                .replace("{bucketId}", bucketId)
                .replace("{fileId}", fileId)

        val apiParams =
            listOf(
                ClientParam.StringParam("name", name),
                ClientParam.ListParam("permissions", permissions ?: emptyList()),
            )
        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Put,
            apiPath,
            File.serializer(),
            apiHeaders,
            apiParams,
        )
    }

    /**
     * Delete File
     *
     * Delete a file by its unique ID. Only users with write permissions have access to delete this resource.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File ID.
     * @return [JsonElement]
     */
    suspend fun deleteFile(
        bucketId: String,
        fileId: String,
    ): JsonElement {
        val apiPath =
            "/storage/buckets/{bucketId}/files/{fileId}"
                .replace("{bucketId}", bucketId)
                .replace("{fileId}", fileId)

        val apiHeaders =
            mutableMapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            JsonElement.serializer(),
            apiHeaders,
        )
    }

    /**
     * Get file for download
     *
     * Get a file content by its unique ID. The endpoint response return with a &#039;Content-Disposition: attachment&#039; header that tells the browser to start downloading the file to user downloads directory.
     *
     * @param bucketId Storage bucket ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File ID.
     * @return [ByteArray]
     */
    suspend fun getFileDownload(
        bucketId: String,
        fileId: String,
        onProgress: ((Progress) -> Unit)? = null,
    ): ByteArray {
        val apiPath =
            "/storage/buckets/{bucketId}/files/{fileId}/download"
                .replace("{bucketId}", bucketId)
                .replace("{fileId}", fileId)

        val apiParams =
            listOf(
                ClientParam.StringParam("project", client.config["project"]),
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            ByteArraySerializer(),
            params = apiParams,
            onDownload = onProgress,
        )
    }

    /**
     * Get file preview
     *
     * Get a file preview image. Currently, this method supports preview for image files (jpg, png, and gif), other supported formats, like pdf, docs, slides, and spreadsheets, will return the file icon image. You can also pass query string arguments for cutting and resizing your preview image. Preview is supported only for image files smaller than 10MB.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File ID
     * @param width Resize preview image width, Pass an integer between 0 to 4000.
     * @param height Resize preview image height, Pass an integer between 0 to 4000.
     * @param gravity Image crop gravity. Can be one of center,top-left,top,top-right,left,right,bottom-left,bottom,bottom-right
     * @param quality Preview image quality. Pass an integer between 0 to 100. Defaults to 100.
     * @param borderWidth Preview image border in pixels. Pass an integer between 0 to 100. Defaults to 0.
     * @param borderColor Preview image border color. Use a valid HEX color, no # is needed for prefix.
     * @param borderRadius Preview image border radius in pixels. Pass an integer between 0 to 4000.
     * @param opacity Preview image opacity. Only works with images having an alpha channel (like png). Pass a number between 0 to 1.
     * @param rotation Preview image rotation in degrees. Pass an integer between -360 and 360.
     * @param background Preview image background color. Only works with transparent images (png). Use a valid HEX color, no # is needed for prefix.
     * @param output Output format type (jpeg, jpg, png, gif and webp).
     * @return [ByteArray]
     */
    @JvmOverloads
    suspend fun getFilePreview(
        bucketId: String,
        fileId: String,
        width: Long? = null,
        height: Long? = null,
        gravity: ImageGravity? = null,
        quality: Long? = null,
        borderWidth: Long? = null,
        borderColor: String? = null,
        borderRadius: Long? = null,
        opacity: Double? = null,
        rotation: Long? = null,
        background: String? = null,
        output: ImageFormat? = null,
        onProgress: ((Progress) -> Unit)? = null,
    ): ByteArray {
        val apiPath =
            "/storage/buckets/{bucketId}/files/{fileId}/preview"
                .replace("{bucketId}", bucketId)
                .replace("{fileId}", fileId)

        val apiParams =
            listOf(
                ClientParam.StringParam("width", width?.toString()),
                ClientParam.StringParam("height", height?.toString()),
                ClientParam.StringParam("gravity", gravity?.value),
                ClientParam.StringParam("quality", quality?.toString()),
                ClientParam.StringParam("borderWidth", borderWidth?.toString()),
                ClientParam.StringParam("borderColor", borderColor),
                ClientParam.StringParam("borderRadius", borderRadius?.toString()),
                ClientParam.StringParam("opacity", opacity?.toString()),
                ClientParam.StringParam("rotation", rotation?.toString()),
                ClientParam.StringParam("background", background),
                ClientParam.StringParam("output", output?.value),
                ClientParam.StringParam("project", client.config["project"]),
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            ByteArraySerializer(),
            params = apiParams,
            onDownload = onProgress,
        )
    }

    /**
     * Get file for view
     *
     * Get a file content by its unique ID. This endpoint is similar to the download method but returns with no  &#039;Content-Disposition: attachment&#039; header.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File ID.
     * @return [ByteArray]
     */
    suspend fun getFileView(
        bucketId: String,
        fileId: String,
        onProgress: ((Progress) -> Unit)? = null,
    ): ByteArray {
        val apiPath =
            "/storage/buckets/{bucketId}/files/{fileId}/view"
                .replace("{bucketId}", bucketId)
                .replace("{fileId}", fileId)

        val apiParams =
            listOf(
                ClientParam.StringParam("project", client.config["project"]),
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            ByteArraySerializer(),
            params = apiParams,
            onDownload = onProgress,
        )
    }
}
