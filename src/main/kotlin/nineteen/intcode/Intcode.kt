package nineteen.intcode

import java.lang.Error
import java.lang.Exception

class UnknownModeException(mode: Long) : Exception("unknown Intcode mode $mode")

class DiagnosticsFailedError(code: Long) : Error("diagnostics failed with code $code")

class NoOutputException : Error("no output available")

data class Context(
    val program: MutableMap<Long, Long>,
    var ip: Long = 0,
    val input: MutableList<Long> = mutableListOf(),
    var output: Long? = null,
    var rb: Long = 0
) {
    val halted: Boolean
        get() = program[ip] == 99L
}

object Intcode {
    private fun parseParameters(ctx: Context, instruction: Instruction, data: Long): LongArray {
        val modes = mutableListOf<Long>()
        var div = 1
        repeat(instruction.numParameters) {
            val mode = (data / div) % 10
            modes.add(mode)
            div *= 10
        }

        val intcodeItems =
            (ctx.ip + 1..ctx.ip + instruction.numParameters).map { ctx.program.getOrDefault(it, 0) }

        // This parsing algorithm assumes that the address parameters are always the last parameters
        val parameters = mutableListOf<Long>()
        var i = 0
        // Get value parameters
        repeat(instruction.numValueParameters) {
            val mode = modes[i]
            val item = intcodeItems[i]
            val valueParameter = when (mode) {
                0L -> ctx.program.getOrDefault(item, 0) // Position
                1L -> item // Immediate
                2L -> ctx.program.getOrDefault(ctx.rb + item, 0) // Relative
                else -> throw UnknownModeException(mode)
            }
            parameters.add(valueParameter)
            i++
        }
        // Get address parameters, if there are any
        repeat(instruction.numAddressParameters) {
            val mode = modes[i]
            val item = intcodeItems[i]
            val addressParameter = when (mode) {
                0L -> item // Position
                2L -> ctx.rb + item // Relative
                else -> throw UnknownModeException(mode)
            }
            parameters.add(addressParameter)
            i++
        }

        return parameters.toLongArray()
    }

    fun step(ctx: Context) {
        ctx.apply {
            val item = program.getOrDefault(ip, 0L)
            val instruction = when (item % 100) {
                1L -> Add
                2L -> Multiply
                3L -> Input
                4L -> Output
                5L -> CompareToZeroConditionalJump { a: Long, b: Long -> a != b }
                6L -> CompareToZeroConditionalJump(Long::equals)
                7L -> CompareParametersConditionalSet { a: Long, b: Long -> a < b }
                8L -> CompareParametersConditionalSet(Long::equals)
                9L -> AdjustRelativeBase
                else -> throw UnknownInstructionException(ip, item)
            }
            val params = parseParameters(ctx, instruction, item / 100)
            instruction.evaluate(this, *params)
            ip += instruction.ipIncrement
        }
    }

    fun stepUntilHalted(ctx: Context) {
        while (!ctx.halted) {
            step(ctx)
        }
    }

    fun stepThroughDiagnostics(ctx: Context): Long {
        while (!ctx.halted) {
            step(ctx)
            if (ctx.output != 0L) {
                break
            }
        }
        val diagnosticCode = ctx.output ?: throw NoOutputException()
        if (!ctx.halted) {
            throw DiagnosticsFailedError(diagnosticCode)
        }
        return diagnosticCode
    }

    // Returns null if the program halted before an output instruction was reached
    fun stepUntilHaltedOrNewOutput(ctx: Context): Long? {
        ctx.output = null
        while (!ctx.halted && ctx.output == null) {
            step(ctx)
        }
        return ctx.output
    }

    fun stepUntilNewOutput(ctx: Context): Long {
        return stepUntilHaltedOrNewOutput(ctx) ?: throw NoOutputException()
    }
}