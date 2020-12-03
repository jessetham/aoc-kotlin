package nineteen.intcode

import java.lang.Exception

class UnknownInstructionException(position: Long, instructionCode: Long) :
    Exception("unknown Intcode instruction $instructionCode at position $position")

class NoInputException : Exception("no input available")

class ParameterSizeException(expected: Int, got: Int) : Exception("expected $expected parameters, got $got")

sealed class Instruction(
    val numParameters: Int,
    val numValueParameters: Int,
) {
    val numAddressParameters: Int = numParameters - numValueParameters
    var ipIncrement: Int = numParameters + 1
    open fun evaluate(ctx: Context, vararg params: Long) {
        if (params.size != numParameters) {
            throw ParameterSizeException(numParameters, params.size)
        }
    }
}

object Add : Instruction(3, 2) {
    override fun evaluate(ctx: Context, vararg params: Long) {
        super.evaluate(ctx, *params)
        val (a, b, to) = params
        ctx.apply {
            program[to] = a + b
        }
    }
}

object Multiply : Instruction(3, 2) {
    override fun evaluate(ctx: Context, vararg params: Long) {
        super.evaluate(ctx, *params)
        val (a, b, to) = params
        ctx.apply {
            program[to] = a * b
        }
    }
}

object Input : Instruction(1, 0) {
    override fun evaluate(ctx: Context, vararg params: Long) {
        super.evaluate(ctx, *params)
        val (to) = params
        ctx.apply {
            if (input.size == 0)
                throw NoInputException()
            program[to] = input.removeFirst()
        }
    }
}

object Output : Instruction(1, 1) {
    override fun evaluate(ctx: Context, vararg params: Long) {
        super.evaluate(ctx, *params)
        val (value) = params
        ctx.apply {
            output = value
        }
    }
}

class CompareToZeroConditionalJump(val compare: (Long, Long) -> Boolean) : Instruction(2, 2) {
    override fun evaluate(ctx: Context, vararg params: Long) {
        super.evaluate(ctx, *params)
        val (value, addr) = params
        if (compare(value, 0)) {
            ctx.ip = addr
            ipIncrement = 0
        }
    }
}

class CompareParametersConditionalSet(val compare: (Long, Long) -> Boolean) : Instruction(3, 2) {
    override fun evaluate(ctx: Context, vararg params: Long) {
        super.evaluate(ctx, *params)
        val (a, b, to) = params
        ctx.apply {
            program[to] = if (compare(a, b)) 1 else 0
        }
    }
}

object AdjustRelativeBase : Instruction(1, 1) {
    override fun evaluate(ctx: Context, vararg params: Long) {
        super.evaluate(ctx, *params)
        val (a) = params
        ctx.apply {
            rb += a
        }
    }
}