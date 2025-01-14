package ch01

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.sangho.app.ch01.Invoice
import org.sangho.app.ch01.Performance
import org.sangho.app.ch01.Play
import org.sangho.app.ch01.Statement
import kotlin.test.assertEquals

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
        val expect = """
            청구내역 (고객명: BigCo)
            Hamlet: $650 55석
            As You Like It: $580 35석
            Othello: $500 40석
            총액: $1,730.00
            적립 포인트: 47점
        """.trimIndent()
        assertEquals(expect, statement)
    }
}