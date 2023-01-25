
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadResponse(
    @SerialName("payload")
    val payload: Payload
) {
    @Serializable
    data class Payload(
        @SerialName("data_file")
        val dataFile: String,
        @SerialName("filetype")
        val filetype: String,
        @SerialName("hash")
        val hash: String,
        @SerialName("id")
        val id: Int
    )
}