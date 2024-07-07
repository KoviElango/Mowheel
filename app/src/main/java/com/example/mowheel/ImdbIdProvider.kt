package com.example.mowheel

import android.content.Context
import android.util.Log
import java.io.IOException
import java.io.InputStreamReader
import kotlin.random.Random

class ImdbIdNotFoundException(message: String) : Exception(message)

open class ImdbIdProvider(private val context: Context) {

    open fun getRandomImdbId(csvFileName: String = "tconst_dataset.csv"): String {
        val imdbIds = mutableListOf<String>()
        try {
            // Log the available files in the assets directory
            val assetFiles = context.assets.list("")
            Log.d("ImdbIdProvider", "Available files in assets: ${assetFiles?.joinToString(", ")}")

            // Verify the file exists
            if (assetFiles?.contains(csvFileName) == false) {
                throw ImdbIdNotFoundException("CSV file not found in assets: $csvFileName")
            }

            context.assets.open(csvFileName).use { inputStream ->
                InputStreamReader(inputStream).use { reader ->
                    reader.readLines().forEach { line ->
                        imdbIds.add(line.trim())
                    }
                }
            }
        } catch (e: IOException) {
            Log.e("ImdbIdProvider", "Error reading CSV: ${e.message}")
            throw ImdbIdNotFoundException("Failed to retrieve IMDb ID from CSV")
        }

        if (imdbIds.isEmpty()) {
            throw ImdbIdNotFoundException("No IMDb IDs found in CSV")
        }

        return imdbIds[Random.nextInt(imdbIds.size)]
    }
}
