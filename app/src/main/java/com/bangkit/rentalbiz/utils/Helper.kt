package com.bangkit.rentalbiz.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.net.toFile
import com.bangkit.rentalbiz.R
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.Executors

object Helper {
    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    private val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    /*
    * Parse error message from api
    * */
    fun parseErrorMessage(errorResponse: String): String {
        return try {
            val jsonObject = JSONObject(errorResponse)
            val errorMessage = jsonObject.optString("error", "Unknown error")
            errorMessage
        } catch (e: JSONException) {
            "Unknown error"
        }
    }

    /*
    * Simplify price
    * */
    fun formatPrice(number: Long): String {
        if (number == 0.toLong()) return "0"
        val suffixes = arrayOf("", "k", "M", "B", "T") // Suffixes for different magnitudes

        val formatter = DecimalFormat("#,##0.#") // Format the number with one decimal place

        var magnitude = 0
        var num = number.toDouble()

        while (num >= 1000 && magnitude < suffixes.size - 1) {
            magnitude++
            num /= 1000.0
        }

        return formatter.format(num) + suffixes[magnitude]
    }

    fun dateToReadable(date: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)

        val localDate = LocalDate.parse(date, dateFormatter)

        return localDate.format(outputFormatter)
    }

    fun dateServerToReadable(date: String): String {
        val inputFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH)

        val localDate = LocalDate.parse(date, inputFormatter)
        return localDate.format(outputFormatter)
    }

    fun dateToServerFormat(date: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

        val localDate = LocalDate.parse(date, dateFormatter)
        val localDateTime = LocalDateTime.of(localDate, LocalTime.of(0, 0))
        return localDateTime.format(dateTimeFormatter)
    }

    fun calculateDaysLeftFromDate(date: String): Long {
        val currentDate = LocalDate.now()
        val targetDateTime = ZonedDateTime.parse(date)

        val targetDate = targetDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDate()

        return ChronoUnit.DAYS.between(currentDate, targetDate)
    }

    private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
    private const val PHOTO_EXTENSION = ".jpg"

    fun ImageCapture.takePicture(
        context: Context,
        lensFacing: Int,
        onImageCaptured: (Uri, Boolean) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        val outputDirectory = context.getOutputDirectory()
        // Create output file to hold the image
        val photoFile = createFile(outputDirectory, FILENAME, PHOTO_EXTENSION)
        val outputFileOptions = getOutputFileOptions(lensFacing, photoFile)

        this.takePicture(
            outputFileOptions,
            Executors.newSingleThreadExecutor(),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                    // If the folder selected is an external media directory, this is
                    // unnecessary but otherwise other apps will not be able to access our
                    // images unless we scan them using [MediaScannerConnection]
                    val mimeType = MimeTypeMap.getSingleton()
                        .getMimeTypeFromExtension(savedUri.toFile().extension)
                    MediaScannerConnection.scanFile(
                        context,
                        arrayOf(savedUri.toFile().absolutePath),
                        arrayOf(mimeType)
                    ) { _, uri ->

                    }
                    onImageCaptured(savedUri, false)
                }

                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }
            })
    }


    fun getOutputFileOptions(
        lensFacing: Int,
        photoFile: File
    ): ImageCapture.OutputFileOptions {

        // Setup image capture metadata
        val metadata = ImageCapture.Metadata().apply {
            // Mirror image when using the front camera
            isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
        }
        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
            .setMetadata(metadata)
            .build()

        return outputOptions
    }

    fun createFile(baseFolder: File, format: String, extension: String) =
        File(
            baseFolder, SimpleDateFormat(format, Locale.US)
                .format(System.currentTimeMillis()) + extension
        )


    fun Context.getOutputDirectory(): File {
        val mediaDir = this.externalMediaDirs.firstOrNull()?.let {
            File(it, this.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else this.filesDir
    }

    fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }

}