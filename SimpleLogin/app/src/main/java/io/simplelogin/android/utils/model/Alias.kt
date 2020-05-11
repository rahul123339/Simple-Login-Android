package io.simplelogin.android.utils.model

import android.content.Context
import android.os.Parcelable
import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat
import androidx.core.text.color
import com.google.gson.annotations.SerializedName
import io.simplelogin.android.R
import io.simplelogin.android.utils.SLDateTimeFormatter
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Alias(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("creation_date") val creationDate: String,
    @SerializedName("creation_timestamp") val creationTimestamp: Long,
    @SerializedName("enabled") private var _enabled: Boolean,
    @SerializedName("note") private var _note: String?,
    @SerializedName("nb_block") private var _blockCount: Int,
    @SerializedName("nb_forward") private var _forwardCount: Int,
    @SerializedName("nb_reply") private var _replyCount: Int,
    @SerializedName("latest_activity") val latestActivity: LatestActivity?
) : Parcelable {
    // Expose enabled
    val enabled: Boolean
        get() = _enabled

    fun setEnabled(enabled: Boolean) {
        _enabled = enabled
    }

    // Expose note
    val note: String?
        get() {
            if (_note == "") return null
            return _note
        }

    fun setNote(note: String?) {
        _note = note
    }

    // Expose blockCount
    val blockCount: Int
        get() = _blockCount

    fun setBlockCount(count: Int) {
        _blockCount = count
    }

    // Expose forwardCount
    val forwardCount: Int
        get() = _forwardCount

    fun setForwardCount(count: Int) {
        _forwardCount = count
    }

    // Expose replyCount
    val replyCount: Int
        get() = _replyCount

    fun setReplyCount(count: Int) {
        _replyCount = count
    }

    val handleCount: Int
        get() = blockCount + forwardCount + replyCount

    @IgnoredOnParcel
    private var _countSpannableString: Spannable? = null
    fun getCountSpannableString(context: Context): Spannable {
        if (_countSpannableString == null) {
            val darkGrayColor = ContextCompat.getColor(context, R.color.colorDarkGray)
            val blackColor = ContextCompat.getColor(context, android.R.color.black)
            val spannableString = SpannableStringBuilder()
                .color(blackColor) { append(" $forwardCount ") }
                .color(darkGrayColor) { append(if (forwardCount > 1) "forwards," else "forwards,") }
                .color(blackColor) { append(" $blockCount ") }
                .color(darkGrayColor) { append(if (blockCount > 1) "blocks," else "blocks,") }
                .color(blackColor) { append(" $replyCount ") }
                .color(darkGrayColor) { append(if (replyCount > 1) "replies," else "reply") }

            _countSpannableString = spannableString
        }

        return _countSpannableString!!
    }

    @IgnoredOnParcel
    private var _creationString: String? = null
    fun getCreationString(): String {
        if (_creationString == null) {
            val distance = SLDateTimeFormatter.distanceFromNow(creationTimestamp)
            _creationString = "Created ${distance.first} ${distance.second} ago"
        }

        return _creationString!!
    }

    @IgnoredOnParcel
    private var _preciseCreationString: String? = null
    fun getPreciseCreationString(): String {
        if (_preciseCreationString == null) {
            _preciseCreationString =
                SLDateTimeFormatter.preciseCreationDateStringFrom(creationTimestamp, "Created on")
        }

        return _preciseCreationString!!
    }

    @IgnoredOnParcel
    private var _latestActivityString: String? = null
    fun getLatestActivityString(): String? {
        return when (latestActivity) {
            null -> null
            else -> {
                if (_latestActivityString == null) {
                    val distance = SLDateTimeFormatter.distanceFromNow(latestActivity.timestamp)
                    _latestActivityString = "${latestActivity.contact.email} • ${distance.first} ${distance.second} ago"
                }

                _latestActivityString!!
            }
        }
    }
}

data class AliasArray(
    @SerializedName("aliases") val aliases: List<Alias>
)