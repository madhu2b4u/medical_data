package com.demo.med.home.data.repository


import androidx.lifecycle.liveData
import com.demo.med.common.Result
import com.demo.med.database.entites.HealthData
import com.demo.med.database.source.LocalDataSource
import com.demo.med.home.data.models.AssociatedDrug
import com.demo.med.home.data.models.DrugsResponse
import com.demo.med.home.data.remote.source.HomeRemoteDataSource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val remoteDataSource: HomeRemoteDataSource,
    private val localDataSource: LocalDataSource
) : HomeRepository {
    override suspend fun drugsRequest(isLoaded: Boolean) = liveData {
        emit(Result.loading())
        try {
            var healthData: MutableList<HealthData>? = null
            if (isLoaded)
                healthData = localDataSource.geHealthData()

            if (healthData == null) {
                val response = remoteDataSource.drugsRequest()
                healthData = flattenData(response)
                localDataSource.saveHealthData(healthData)
            }
            emit(Result.success(healthData))

        } catch (exception: Exception) {
            emit(Result.error(exception.message ?: "", null))
        }
    }

    private fun flattenData(data: DrugsResponse): MutableList<HealthData> {

        val result = mutableListOf<HealthData>()
        data.problems.forEach { problem ->
            problem.diabetes.forEach { diabetes ->
                val problemName = diabetes.javaClass.simpleName
                diabetes.medications.forEach { medication ->
                    medication.medicationsClasses.forEach { medicationClass ->
                        medicationClass.className.forEach { associatedDrug ->
                            associatedDrug.associatedDrug.forEach {
                                addToResult(result, problemName, it)
                            }
                            associatedDrug.associatedDrug2.forEach {
                                addToResult(result, problemName, it)
                            }
                        }

                        medicationClass.className2.forEach { associatedDrug ->
                            associatedDrug.associatedDrug.forEach {
                                addToResult(result, problemName, it)
                            }
                            associatedDrug.associatedDrug2.forEach {
                                addToResult(result, problemName, it)
                            }
                        }
                    }
                }
            }
        }

        return result
    }

    private fun addToResult(
        result: MutableList<HealthData>,
        problemName: String,
        it: AssociatedDrug
    ) {
        result.add(
            HealthData(
                problemName = problemName,
                medicationName = it.name,
                medicationDose = it.dose,
                medicationStrength = it.strength,
            )
        )
    }
}