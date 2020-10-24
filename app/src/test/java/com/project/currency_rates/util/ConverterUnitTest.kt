package com.project.currency_rates.util
import com.project.currency_rates.model.Rate
import com.project.currency_rates.util.Converter.serializeToMap
import org.junit.Test
import org.junit.Assert.*

class ConverterUnitTest {

    @Test
    fun serializeToMap_dataObjectToMap() {
        val rate= Rate (1.0, 2.0, 3.0, 4.0, 5.0 ,
            6.0, 7.0, 8.0, 9.0)
        val serializeToMap = rate.serializeToMap()
        assertEquals(9, serializeToMap.size)
        assertEquals(7.0, serializeToMap.get("CNY"))
    }
}