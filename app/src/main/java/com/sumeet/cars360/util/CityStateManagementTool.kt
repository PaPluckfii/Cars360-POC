package com.sumeet.cars360.util

import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

object CityStateManagementTool {

    fun getParsedStateData(open: InputStream): List<String> {
        val stateObject = JSONObject(loadJSONFromAsset(open))

        val arrayList = arrayListOf<String>()

        val iterator = stateObject.keys()
        while (iterator.hasNext()) {
            val state: String = iterator.next()
            arrayList.add(state)
//            val cityList = stateObject.getJSONArray(state)
//            val listOfCities = arrayListOf<String>()
//            for (i in 0 until cityList.length()){
//                listOfCities.add(cityList[i] as String)
//            }
//            arrayList.add(
//                CityStateEntity(
//                    state,
//                    listOfCities
//                )
//            )
        }

        return arrayList
    }

    fun getParsedCityData(open: InputStream,selectedState: String): List<String> {
        val stateObject = JSONObject(loadJSONFromAsset(open))
        val arrayList = arrayListOf<String>()
        val iterator = stateObject.keys()
        while (iterator.hasNext()) {
            val state: String = iterator.next()
            if (state == selectedState) {
                val cityList = stateObject.getJSONArray(state)
                for (i in 0 until cityList.length()) {
                    arrayList.add(cityList[i] as String)
                }
            }
        }
        return arrayList
    }

    private fun loadJSONFromAsset(open: InputStream): String {
        return try {
            val `is`: InputStream = open
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
    }
}