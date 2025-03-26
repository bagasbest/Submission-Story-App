package com.project.dicodingplayground.submission_fundamental_android.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "favorite")
class FavoriteEntity(
    @field:ColumnInfo(name = "name")
    @field:PrimaryKey
    val name: String

) : Parcelable