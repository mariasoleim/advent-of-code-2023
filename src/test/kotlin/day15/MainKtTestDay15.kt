package day15

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainKtTestDay15 {

    @Test
    fun getLabel() {
        val labelRn = hashAlgorithm("rn")
        assertEquals(0, labelRn)


        val labelQp = hashAlgorithm("qp")
        assertEquals(1, labelQp)


        val labelCm = hashAlgorithm("cm")
        assertEquals(0, labelCm)


        val labelPc = hashAlgorithm("pc")
        assertEquals(3, labelPc)
    }
}