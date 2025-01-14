package org.sangho.app.ch01

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.floor
import kotlin.math.max

class Performance(val playID: String, val audience: Int)

class Invoice(val customer: String, private val performances: List<Performance>) {
    fun getPerformances(): List<Performance> {
        return performances
    }
}

class Plays (vararg plays: Pair<Performance, Play>){
    private val playMap: Map<Performance, Play> = mapOf(*plays)

    fun getPlay(performance: Performance): Play {
        return playMap[performance]!!
    }
}

class Play(val name: String, val type: Type) {
    enum class Type {
        TRAGEDY, COMEDY
    }
}


class Statement {
    fun statement(invoice: Invoice, plays: Plays): String {
        val result = StringBuilder(String.format("청구내역 (고객명: %s)\n", invoice.customer))
        for (performance in invoice.getPerformances()) {
            // 청구 내역을 출력한다.
            result.append(String.format("%s: $%d %d석\n", playFor(plays, performance).name, amountFor(performance, plays) / 100, performance.audience))
        }

        result.append(String.format("총액: $%s\n", dollarFormat(totalAmount(invoice, plays))))
        result.append(String.format("적립 포인트: %d점", totalVolumeCredits(invoice, plays)))
        return result.toString()
    }

    private fun dollarFormat(amount: Double): String {
        return DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(amount)
    }

    private fun totalAmount(invoice: Invoice, plays: Plays): Double {
        var totalAmount = 0
        for (performance in invoice.getPerformances()) {
            totalAmount += amountFor(performance, plays)
        }
        return totalAmount.toDouble() / 100
    }

    private fun totalVolumeCredits(invoice: Invoice, plays: Plays): Int {
        var volumeCredit = 0
        for (performance in invoice.getPerformances()) {
            volumeCredit += volumeCreditFor(plays, performance)
        }
        return volumeCredit
    }

    private fun volumeCreditFor(plays: Plays, performance: Performance): Int {
        var result = 0

        // 포인트를 적립한다.
        result += max(performance.audience - 30, 0)

        // 희극 관객 5명마다 추가 포인트를 제공핟나.
        if (playFor(plays, performance).type == Play.Type.COMEDY) {
            result = (result + floor((performance.audience / 5).toDouble())).toInt()
        }

        return result
    }

    private fun amountFor(performance: Performance, plays: Plays): Int {
        var result: Int
        when (plays.getPlay(performance).type) {
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

            else -> throw java.lang.Exception("알 수 없는 장르")
        }
        return result
    }

    private fun playFor(plays: Plays, performance: Performance): Play {
        return plays.getPlay(performance)
    }

}
