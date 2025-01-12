package ch01

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.sangho.app.ch01.Invoice
import org.sangho.app.ch01.Performance
import org.sangho.app.ch01.Play
import org.sangho.app.ch01.Statement

class StatementTest {
    private lateinit var plays: Map<Performance, Play>
    private lateinit var invoice: Invoice

    @BeforeEach
    fun setUp() {
        // read from plays.json and invoices.json
        val hamlet = Performance("hamlet", 55)
        val asLike = Performance("as-like", 35)
        val othello = Performance("othello", 40)
        invoice = Invoice("BigCo", listOf(hamlet, asLike, othello))
        plays = mapOf(
            hamlet to Play("Hamlet", Play.Type.TRAGEDY),
            asLike to Play("As You Like It", Play.Type.COMEDY),
            othello to Play("Othello", Play.Type.TRAGEDY)
        )
    }

    @Test
    fun testStatement() {
        val statement = Statement().statement(invoice, plays)
        println(statement)
    }
}