package com.binar.challenge4.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Student (
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "judul")
    var judul: String = "",
    @ColumnInfo(name = "catatan")
    var catatan: String = ""
): Parcelable