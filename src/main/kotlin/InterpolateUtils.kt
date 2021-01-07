import java.lang.IllegalStateException
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

/**
 * @author Kris | 07/01/2021
 */
object InterpolateUtils {

    fun interpolate(low: Int, high: Int, level: Int) : Double {
        val value = floor(low * (99 - level) / 98.0) + floor(high * (level - 1) / 98.0) + 1
        return min(max((value / 256), 0.0), 1.0)
    }

    fun cascadeInterpolate(bounds: Array<Bound>, level: Int, index: Int) : Double {
        assert(bounds contentEquals bounds.copyOf().sortedByDescending { it.req }.toTypedArray()) { "Elements array is not sorted." }
        var rate = 1.0
        for (i in bounds.indices) {
            val v = bounds[i]
            if (i == index) {
                rate *= interpolate(v.low, v.high, level)
                return rate
            }
            if (level >= v.req) {
                rate *= 1 - interpolate(v.low, v.high, level)
            }
        }
        throw IllegalStateException("Index out of bounds")
    }

    data class Bound(val low: Int, val high: Int, val req: Int)
}