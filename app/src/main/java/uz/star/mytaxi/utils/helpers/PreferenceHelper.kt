package uz.star.mytaxi.utils.helpers

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Sherzodbek Muhammadiev on 31.01.2020
 */

class BooleanPreference(
    private val pref: SharedPreferences,
    private val defValue: Boolean = false
) : ReadWriteProperty<Any, Boolean> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = pref.getBoolean(property.name, defValue)
    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) = pref.edit().putBoolean(property.name, value).apply()
}

class IntPreference(
    private val pref: SharedPreferences,
    private val defValue: Int = -1
) : ReadWriteProperty<Any, Int> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = pref.getInt(property.name, defValue)
    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) = pref.edit().putInt(property.name, value).apply()
}

class LongPreference(
    private val pref: SharedPreferences,
    private val defValue: Long = 0L
) : ReadWriteProperty<Any, Long> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = pref.getLong(property.name, defValue)
    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) = pref.edit().putLong(property.name, value).apply()
}

class StringPreference(
    private val pref: SharedPreferences,
    private val defValue: String = ""
) : ReadWriteProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String = pref.getString(property.name, defValue) ?: ""
    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) = pref.edit().putString(property.name, value).apply()
}

class DoublePreference(
    private val pref: SharedPreferences,
    private val defValue: Double = 0.0
) : ReadWriteProperty<Any, Double> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Double =
        java.lang.Double.longBitsToDouble(pref.getLong(property.name, java.lang.Double.doubleToLongBits(defValue)))

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Double) =
        pref.edit().putLong(property.name, java.lang.Double.doubleToRawLongBits(value)).apply()
}

class StringSetPreference(
    private val pref: SharedPreferences,
    private val defValue: Set<String> = setOf()
) : ReadWriteProperty<Any, Set<String>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Set<String> = pref.getStringSet(property.name, defValue) ?: setOf()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Set<String>) = pref.edit().putStringSet(property.name, value).apply()
}

class StringListPreference(
    private val pref: SharedPreferences,
    private val defValue: List<String> = listOf()
) : ReadWriteProperty<Any, List<String>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): List<String> {
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return try {
            val json = pref.getString(property.name, "")
            Gson().fromJson(json, type) ?: defValue
        } catch (e: Exception) {
            listOf()
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: List<String>) =
        pref.edit().putString(property.name, Gson().toJson(value)).apply()
}