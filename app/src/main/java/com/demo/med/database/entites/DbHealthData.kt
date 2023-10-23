package com.demo.med.database.entites

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "db_healthdata")
data class DbHealthData(
    @PrimaryKey val id: Int,
    val news: String
)

@Parcelize
data class HealthData(
    val problemName: String,
    val medicationName: String?,
    val medicationDose: String?,
    val medicationStrength: String?,
) : Parcelable
