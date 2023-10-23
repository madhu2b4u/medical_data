package com.demo.med.home.data.models


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrugsResponse(
    @Expose @SerializedName("problems")
    val problems: List<Problem>
) : Parcelable


@Parcelize
data class Problem(
    @Expose @SerializedName("Asthma")
    val asthma: List<Asthma>,
    @Expose @SerializedName("Diabetes")
    val diabetes: List<Diabete>
) : Parcelable

@Parcelize
class Asthma : Parcelable

@Parcelize
data class Diabete(
    @Expose @SerializedName("labs")
    val labs: List<Lab>,
    @Expose @SerializedName("medications")
    val medications: List<Medication>
) : Parcelable


@Parcelize
data class Medication(
    @Expose @SerializedName("medicationsClasses")
    val medicationsClasses: List<MedicationsClasse>
) : Parcelable

@Parcelize
data class Lab(
    @Expose @SerializedName("missing_field")
    val missingField: String
) : Parcelable


@Parcelize
data class MedicationsClasse(
    @Expose @SerializedName("className")
    val className: List<ClassName>,
    @Expose @SerializedName("className2")
    val className2: List<ClassName>
) : Parcelable

@Parcelize
data class ClassName(
    @Expose @SerializedName("associatedDrug")
    val associatedDrug: List<AssociatedDrug>,
    @Expose @SerializedName("associatedDrug#2")
    val associatedDrug2: List<AssociatedDrug>
) : Parcelable

@Parcelize
data class AssociatedDrug(
    @Expose @SerializedName("dose")
    val dose: String,
    @Expose @SerializedName("name")
    val name: String,
    @Expose @SerializedName("strength")
    val strength: String
) : Parcelable