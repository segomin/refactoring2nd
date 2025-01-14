package org.sangho.app.ch01

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
}