package day16

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainKtTestDay16 {

    @Test
    fun movementMatch() {
        val tile = Tile(0, 0, '/', TileType.MIRROR_LEANING_RIGHT)
        val movement1 = Movement(tile, Direction.RIGHT)
        val movement2 = Movement(tile.energize(), Direction.RIGHT)
        assertTrue(movement1.matches(movement2))
    }
}
