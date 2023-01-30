package com.github.theapache64.todotlottie

import kotlinx.coroutines.runBlocking
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.net.URL

fun main(args: Array<String>) = runBlocking {
    val projectDir = File(System.getProperty("user.dir"))
    println("➡️ Initializing...")
    projectDir.walk()
        .forEach { jsonFile ->
            val isAndroidRawFile = !jsonFile.path.contains("/build/") &&
                    jsonFile.path.contains("/raw-?[a-zA-z\\-]*/".toRegex()) &&
                    jsonFile.extension == "json"
            if (
                isAndroidRawFile // TODO || iOSLottieFile || webLottieFile
            ) {
                try {
                    val json = JSONObject(jsonFile.readText())
                    val isLottieJson = json.has("v")
                    if (isLottieJson) {
                        convertToDotLottie(jsonFile)
                        jsonFile.delete()
                        println("------------------------------")
                    } else {
                        println("⚠️ Not a lottie JSON. Skipping ${jsonFile.absolutePath}")
                    }
                } catch (e: JSONException) {
                    println("⚠️ Not a lottie JSON. Skipping ${jsonFile.absolutePath}")
                }
            }
        }

    println("✅ Done")
}

suspend fun convertToDotLottie(
    jsonFile: File
): File {
    val fileUrl = uploadFile(jsonFile)
    val convertedUrl = convert(fileUrl)
    return download(jsonFile, convertedUrl)
}

fun download(jsonFile: File, convertedUrl: String): File {
    println("⬇️ Downloading...")
    val targetFile = File("${jsonFile.parent}${File.separator}${jsonFile.nameWithoutExtension}.lottie")
    targetFile.delete()
    URL(convertedUrl).openStream().use { input ->
        FileOutputStream(
            targetFile
        ).use { output ->
            input.copyTo(output)
        }
    }

    return targetFile.also {
        println("✅ Downloaded -> ${it.absolutePath}")
    }
}

suspend fun convert(fileUrl: String): String {
    println("🔀 Converting '$fileUrl'...")
    val fileName = convertApi.toDotLottie(
        ConvertRequest(
            url = fileUrl
        )
    ).file

    return "https://lottie-editor-api-temp.s3.amazonaws.com/$fileName".also {
        println("✅ Converted -> $it")
    }
}

suspend fun uploadFile(jsonFile: File): String {
    println("🆙 Uploading '${jsonFile.name}...'")
    val respJson = uploadApi.tempFileUpload(
        UploadRequest(
            payload = jsonFile.readText()
        )
    )
    return respJson.payload.dataFile.also {
        println("✅ Uploaded -> $it")
    }
}
