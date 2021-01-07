import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * @author Kris | 07/01/2021
 * All actual asserted percentages found on the [raw shrimp wiki page](https://oldschool.runescape.wiki/w/Raw_shrimps).
 */
internal class InterpolateUtilsTest {

    private val shrimp = InterpolateUtils.Bound(48, 256, 1)
    private val anchovies = InterpolateUtils.Bound(24, 128, 15)

    /**
     * It is important to note that the anchovies must be first in this array, or else it will return invalid values.
     */
    private val fish = arrayOf(anchovies, shrimp)

    @Test
    fun `interpolate singular element shrimp at level 10`() {
        val interpolatedValue = InterpolateUtils.interpolate(48, 256, 10)
        val roundedPercentage = String.format("%.2f", interpolatedValue * 100).toDouble()
        assertEquals(roundedPercentage, 26.17)
    }

    @Test
    fun `test if cascade function returns correct elements`() {
        val shrimpAtLevel25 = calculateFormattedPercentage(25, shrimp)
        assertEquals(shrimpAtLevel25, 31.12)

        val shrimpAtLevel10 = calculateFormattedPercentage(10, shrimp)
        assertEquals(shrimpAtLevel10, 26.17)

        val shrimpAtLevel99 = calculateFormattedPercentage(99, shrimp)
        assertEquals(shrimpAtLevel99, 49.61)

        val anchoviesAtLevel25 = calculateFormattedPercentage(25, anchovies)
        assertEquals(anchoviesAtLevel25, 19.53)

        val anchoviesAtLevel47 = calculateFormattedPercentage(47, anchovies)
        assertEquals(anchoviesAtLevel47, 28.52)

        val anchoviesAtLevel99 = calculateFormattedPercentage(99, anchovies)
        assertEquals(anchoviesAtLevel99, 50.39)
    }

    private fun calculateFormattedPercentage(level: Int, element: InterpolateUtils.Bound) : Double {
        val index = fish.indexOf(element)
        val cascadedInterpolatedValue = InterpolateUtils.cascadeInterpolate(fish, level, index)
        return String.format("%.2f", cascadedInterpolatedValue * 100).toDouble()
    }
}