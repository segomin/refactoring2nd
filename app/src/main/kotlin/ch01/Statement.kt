package org.sangho.app.ch01

import kotlin.math.floor

class Invoice(val customer: String, val age: Int) {
    fun getPerformances(): List<Performance> {
        TODO("Not yet implemented")
    }
}

class Play(val name: String, val type: Type) {
    enum class Type {
        TRAGEDY, COMEDY
    }
}

class Performance() {
    fun getAudience(): Int {
        TODO("Not yet implemented")
    }
}

class Statement {
    @Throws(Exception::class)
    fun statement(invoice: Invoice, plays: Map<Performance, Play>): String {
        var totalAmount = 0
        var volumeCredit = 0
        val result = StringBuilder(String.format("청구내역 (고객명: %s)\n", invoice.customer))
        for (performance in invoice.getPerformances()) {
            val play: Play = plays[performance]!!
            var thisAmount = 0

            when (play.type) {
                Play.Type.TRAGEDY -> {
                    thisAmount = 40000
                    if (performance.getAudience() > 30) {
                        thisAmount += 1000 * (performance.getAudience() - 30)
                    }
                }
                Play.Type.COMEDY -> {
                    thisAmount = 30000
                    if (performance.getAudience() > 20) {
                        thisAmount += 10000 + 500 * (performance.getAudience() - 20)
                    }
                    thisAmount += 300 * performance.getAudience()
                }
                else -> throw Exception("알 수 없는 장르")
            }

            // 포인트를 적립한다.
            volumeCredit += Math.max(performance.getAudience() - 30, 0)

            // 희극 관객 5명마다 추가 포인트를 제공핟나.
            if (play.type == Play.Type.COMEDY) {
                volumeCredit = (volumeCredit + floor((performance.getAudience().toDouble() / 5))).toInt()
            }

            // 청구 내역을 출력한다.
            result.append(
                java.lang.String.format(
                    "%s: $%d %d석\n",
                    play.name,
                    thisAmount / 100,
                    performance.getAudience()
                )
            )
            totalAmount += thisAmount
        }

        result.append(String.format("총액: $%d\n", totalAmount / 100))
        result.append(String.format("적립 포인트: %d점", volumeCredit))
        return result.toString()
    }
}
