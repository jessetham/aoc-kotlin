package util

import kotlin.math.*

object Coordinates {
    data class Cartesian2D(val x: Int, val y: Int) {
        val magnitude
            get() = sqrt(x.toDouble().pow(2) + y.toDouble().pow(2))

        fun toPolar(): Polar {
            return Polar(magnitude, atan2(y.toDouble(), x.toDouble()))
        }

        operator fun plus(other: Cartesian2D): Cartesian2D {
            return Cartesian2D(x + other.x, y + other.y)
        }

        operator fun minus(other: Cartesian2D): Cartesian2D {
            return Cartesian2D(x - other.x, y - other.y)
        }
    }

    data class Polar(var r: Double, val theta: Double) {
        fun toCartesian(): Cartesian2D {
            return Cartesian2D((r * cos(theta)).roundToInt(), (r * sin(theta)).roundToInt())
        }
    }

    data class Cartesian3D(val x: Int, val y: Int, val z: Int) {
        val magnitude
            get() = sqrt(x.toDouble().pow(2) + y.toDouble().pow(2) + z.toDouble().pow(2))

        operator fun plus(other: Cartesian3D): Cartesian3D {
            return Cartesian3D(x + other.x, y + other.y, z + other.z)
        }

        operator fun minus(other: Cartesian3D): Cartesian3D {
            return Cartesian3D(x - other.x, y - other.y, z - other.z)
        }
    }
}