package com.jamshedalamqaderi.kmp.appwrite.services

import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Service
import com.jamshedalamqaderi.kmp.appwrite.enums.ImageFormat
import com.jamshedalamqaderi.kmp.appwrite.enums.ImageGravity
import com.jamshedalamqaderi.kmp.appwrite.models.File
import com.jamshedalamqaderi.kmp.appwrite.models.FileList
import com.jamshedalamqaderi.kmp.appwrite.models.Progress
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.fileExtensions
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.InternalAPI
import io.ktor.utils.io.readAvailable
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.files.SystemTemporaryDirectory
import kotlin.jvm.JvmOverloads

/**
 * The Storage service allows you to manage your project files.
 **/
class Storage(
    client: Client,
) : Service(client) {
    /**
     * List files
     *
     * Get a list of all the user files. You can use the query params to filter your results.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param queries Array of query strings generated using the Query class provided by the SDK. [Learn more about queries](https://appwrite.io/docs/queries). Maximum of 100 queries are allowed, each 4096 characters long. You may filter on the following attributes: name, signature, mimeType, sizeOriginal, chunksTotal, chunksUploaded
     * @param search Search term to filter your list results. Max length: 256 chars.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.FileList]
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
            mapOf(
                "queries" to queries,
                "search" to search,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<FileList>() },
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
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.File]
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
            mapOf(
                "fileId" to fileId,
                "file" to file,
                "permissions" to permissions,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "multipart/form-data",
            )

        return client.call(
            HttpMethod.Post,
            apiPath,
            apiHeaders,
            apiParams,
            onUpload = onProgress,
            converter = { it.body<File>() },
        )
    }

    /**
     * Get file
     *
     * Get a file by its unique ID. This endpoint response returns a JSON object with the file metadata.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File ID.
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.File]
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
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            converter = { it.body<File>() },
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
     * @return [com.jamshedalamqaderi.kmp.appwrite.models.File]
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
            mapOf(
                "name" to name,
                "permissions" to permissions,
            )
        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )

        return client.call(
            HttpMethod.Put,
            apiPath,
            apiHeaders,
            apiParams,
            converter = { it.body<File>() },
        )
    }

    /**
     * Delete File
     *
     * Delete a file by its unique ID. Only users with write permissions have access to delete this resource.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File ID.
     * @return [Unit]
     */
    suspend fun deleteFile(
        bucketId: String,
        fileId: String,
    ) {
        val apiPath =
            "/storage/buckets/{bucketId}/files/{fileId}"
                .replace("{bucketId}", bucketId)
                .replace("{fileId}", fileId)

        val apiHeaders =
            mapOf(
                "content-type" to "application/json",
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            apiHeaders,
            converter = { },
        )
    }

    /**
     * Get file for download
     *
     * Get a file content by its unique ID. The endpoint response return with a 'Content-Disposition: attachment' header that tells the browser to start downloading the file to user downloads directory.
     *
     * @param bucketId Storage bucket ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File ID.
     * @return [kotlinx.io.files.Path]
     */
    @OptIn(InternalAPI::class)
    suspend fun getFileDownload(
        bucketId: String,
        fileId: String,
        onProgress: ((Progress) -> Unit)? = null,
    ): Path {
        val apiPath =
            "/storage/buckets/{bucketId}/files/{fileId}/download"
                .replace("{bucketId}", bucketId)
                .replace("{fileId}", fileId)

        val apiParams =
            mapOf(
                "project" to client.config["project"],
            )

        return client.call(
            HttpMethod.Get,
            apiPath,
            params = apiParams,
            onDownload = onProgress,
            converter = { response ->
                response.saveTo(SystemTemporaryDirectory, fileId)
            },
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
     * @return [kotlinx.io.files.Path]
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
    ): Path {
        val apiPath =
            "/storage/buckets/{bucketId}/files/{fileId}/preview"
                .replace("{bucketId}", bucketId)
                .replace("{fileId}", fileId)

        val apiParams =
            buildMap {
                put("width", width?.toString())
                put("height", height?.toString())
                put("gravity", gravity?.value)
                put("quality", quality?.toString())
                put("borderWidth", borderWidth?.toString())
                put("borderColor", borderColor)
                put("borderRadius", borderRadius?.toString())
                put("opacity", opacity?.toString())
                put("rotation", rotation?.toString())
                put("background", background)
                put("output", output?.value)
                put("project", client.config["project"])
            }
        return client.call(
            HttpMethod.Get,
            apiPath,
            params = apiParams,
            onDownload = onProgress,
            converter = { response ->
                response.saveTo(SystemTemporaryDirectory, fileId)
            },
        )
    }

    /**
     * Get file for view
     *
     * Get a file content by its unique ID. This endpoint is similar to the download method but returns with no  &#039;Content-Disposition: attachment&#039; header.
     *
     * @param bucketId Storage bucket unique ID. You can create a new storage bucket using the Storage service [server integration](https://appwrite.io/docs/server/storage#createBucket).
     * @param fileId File ID.
     * @return [kotlinx.io.files.Path]
     */
    suspend fun getFileView(
        bucketId: String,
        fileId: String,
        onProgress: ((Progress) -> Unit)? = null,
    ): Path {
        val apiPath =
            "/storage/buckets/{bucketId}/files/{fileId}/view"
                .replace("{bucketId}", bucketId)
                .replace("{fileId}", fileId)

        val apiParams =
            mapOf(
                "project" to client.config["project"],
            )
        return client.call(
            HttpMethod.Get,
            apiPath,
            params = apiParams,
            onDownload = onProgress,
            converter = {
                it.saveTo(SystemTemporaryDirectory, fileId)
            },
        )
    }
}

@OptIn(InternalAPI::class)
/**
 * Save an HTTP response body into the given destination path.
 * If [destination] points to a directory, the file name will be derived from [fileId]
 * and the response content type (when available) to set an appropriate extension.
 */
private suspend fun HttpResponse.saveTo(
    destination: Path,
    fileId: String,
): Path {
    val destinationFile =
        if (SystemFileSystem.metadataOrNull(destination)?.isDirectory == true) {
            val ct = contentType()
            val ctType = ct?.contentType
            val supportedExtensions = ct?.fileExtensions()
            val finalExtension =
                supportedExtensions
                    ?.find { ext -> ctType?.contains(ext) == true }
                    ?: supportedExtensions?.firstOrNull()
            val fileName = if (finalExtension != null) "$fileId.$finalExtension" else fileId
            Path(destination, fileName)
        } else {
            destination
        }
    rawContent.saveTo(destinationFile)
    return destinationFile
}

/**
 * Streams this channel to a file sink in chunks to avoid high memory usage.
 */
private suspend fun ByteReadChannel.saveTo(destination: Path) {
    SystemFileSystem.sink(destination).buffered().use { sink ->
        val buffer = ByteArray(4 * 1024)
        while (true) {
            val count = this@saveTo.readAvailable(buffer, 0, buffer.size)
            if (count == -1) break
            sink.write(buffer, 0, count)
        }
        sink.flush()
    }
}
