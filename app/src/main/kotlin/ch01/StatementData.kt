package org.sangho.app.ch01

class StatementData(val invoice: Invoice, val plays: Plays) {
    fun getCustomer(): String {
        return invoice.customer
    }

    fun getPerformances(): List<Performance> {
        return invoice.getPerformances()
    }
}