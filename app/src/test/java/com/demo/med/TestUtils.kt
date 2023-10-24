package com.demo.med

import com.demo.med.database.entites.HealthData
import com.demo.med.home.data.models.AssociatedDrug
import com.demo.med.home.data.models.Asthma
import com.demo.med.home.data.models.ClassName
import com.demo.med.home.data.models.Diabete
import com.demo.med.home.data.models.DrugsResponse
import com.demo.med.home.data.models.Lab
import com.demo.med.home.data.models.Medication
import com.demo.med.home.data.models.MedicationsClasse
import com.demo.med.home.data.models.Problem

class TestUtils {

    companion object {

        private val asprin = AssociatedDrug("asprin", "", "500 mg")
        private val somethingElse = AssociatedDrug("somethingElse", "", "500 mg")
        private val anotherDrug = AssociatedDrug("anotherDrug", "", "250 mg")
        private val yetAnotherDrug = AssociatedDrug("yetAnotherDrug", "", "100 mg")


        private val className = ClassName(listOf(asprin), listOf(somethingElse))
        private val className2 = ClassName(listOf(anotherDrug), listOf(yetAnotherDrug))

        private val medicationClass1 = MedicationsClasse(listOf(className), listOf(className2))
        private val medicationClass2 = MedicationsClasse(listOf(className), listOf(className2))

        private val medication1 = Medication(listOf(medicationClass1))
        private val medication2 = Medication(listOf(medicationClass2))

        private val lab1 = Lab("missing_value")
        private val lab2 = Lab("some_value")

        private val diabetes1 = Diabete(listOf(lab1), listOf(medication1))
        private val diabetes2 = Diabete(listOf(lab1, lab2), listOf(medication2))

        private val asthma1 = Asthma()

        private val problem1 = Problem(listOf(asthma1), listOf(diabetes1))
        private val problem2 = Problem(listOf(), listOf(diabetes2))

        val response = DrugsResponse(listOf(problem1, problem2))

        val healthDataList = mutableListOf(
            HealthData("Diabete", "", "asprin", "500 mg"),
            HealthData("Diabete", "", "somethingElse", "500 mg"),
            HealthData("Diabete", "", "anotherDrug", "250 mg"),
            HealthData("Diabete", "", "yetAnotherDrug", "100 mg"),
            HealthData("Diabete", "", "asprin", "500 mg"),
            HealthData("Diabete", "", "somethingElse", "500 mg"),
            HealthData("Diabete", "", "anotherDrug", "250 mg"),
            HealthData("Diabete", "", "yetAnotherDrug", "100 mg"),
        )
    }


}