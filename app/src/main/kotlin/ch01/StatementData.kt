package org.sangho.app.ch01

import kotlin.math.floor
import kotlin.math.max

class StatementData(val invoice: Invoice, private val plays: Plays) {
    fun getCustomer(): String {
        return invoice.customer
    }

    fun getPerformances(): List<Performance> {
        return invoice.getPerformances()
    }

    fun playFor(performance: Performance): Play {
        return plays.getPlay(performance)
    }

    fun amountFor(performance: Performance): Int {
        var result = 0
        when (playFor(performance).type) {
            Play.Type.TRAGEDY -> {
                result = 40000
                if (performance.audience > 30) {
                    result += 1000 * (performance.audience - 30)
                }
            }
            Play.Type.COMEDY -> {
                result = 30000
                if (performance.audience > 20) {
                    result += 10000 + 500 * (performance.audience - 20)
                }
                result += 300 * performance.audience
            }
        }
        return result
    }

    fun totalAmount(): Double {
        var totalAmount = 0
        for (performance in getPerformances()) {
            totalAmount += amountFor(performance)
        }
        return totalAmount.toDouble() / 100
    }

    fun totalVolumeCredits(): Int {
        var volumeCredit = 0
        for (performance in getPerformances()) {
            volumeCredit += volumeCreditFor(performance)
        }
        return volumeCredit
    }

    private fun volumeCreditFor(performance: Performance): Int {
        var result = 0

        // 포인트를 적립한다.
        result += max(performance.audience - 30, 0)

        // 희극 관객 5명마다 추가 포인트를 제공핟나.
        if (playFor(performance).type == Play.Type.COMEDY) {
            result = (result + floor((performance.audience / 5).toDouble())).toInt()
        }

        return result
    }
}