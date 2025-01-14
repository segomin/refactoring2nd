package org.sangho.app.ch01

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

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
        val statementData = StatementData(invoice, plays)
        return renderPlainText(statementData)
    }
    fun statementHtml(invoice: Invoice, plays: Plays): String {
        val statementData = StatementData(invoice, plays)
        return renderHtml(statementData)
    }

    private fun renderPlainText(statementData: StatementData): String {
        val result = StringBuilder(String.format("청구내역 (고객명: %s)\n", statementData.getCustomer()))
        for (performance in statementData.getPerformances()) {
            // 청구 내역을 출력한다.
            result.append(String.format("%s: $%d %d석\n", statementData.playFor(performance).name, statementData.amountFor(performance) / 100, performance.audience))
        }

        result.append(String.format("총액: $%s\n", dollarFormat(statementData.totalAmount())))
        result.append(String.format("적립 포인트: %d점", statementData.totalVolumeCredits()))
        return result.toString()
    }

    private fun renderHtml(statementData: StatementData): String {
        val result = java.lang.StringBuilder(String.format("<h1> 청구내역 (고객명: %s)\n </h1>", statementData.getCustomer()))
        result.append("<table> \n")
        result.append("<tr><th> 연극 </th> <th>좌석 수</th> <th>금액</th>")
        for (performance in statementData.getPerformances()) {
            result.append(
                String.format(
                    "<tr><td> %s: </td> <td> $%d </td> <td> %d석 </td></tr>\n",
                    statementData.playFor(performance).name,
                    statementData.amountFor(performance) / 100,
                    performance.audience
                )
            )
        }
        result.append("</table>\n")

        result.append(String.format("총액: $%s\n", dollarFormat(statementData.totalAmount())))
        result.append(String.format("적립 포인트: %d점", statementData.totalVolumeCredits()))
        return result.toString()
    }

    private fun dollarFormat(amount: Double): String {
        return DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).format(amount)
    }
}
