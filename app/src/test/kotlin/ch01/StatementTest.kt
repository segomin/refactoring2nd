package ch01

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.sangho.app.ch01.*
import kotlin.test.assertEquals

class StatementTest {
    private lateinit var plays: Plays
    private lateinit var invoice: Invoice

    @BeforeEach
    fun setUp() {
        // read from plays.json and invoices.json
        val hamlet = Performance("hamlet", 55)
        val asLike = Performance("as-like", 35)
        val othello = Performance("othello", 40)
        invoice = Invoice("BigCo", listOf(hamlet, asLike, othello))
        plays = Plays(
            hamlet to Play("Hamlet", Play.Type.TRAGEDY),
            asLike to Play("As You Like It", Play.Type.COMEDY),
            othello to Play("Othello", Play.Type.TRAGEDY)
        )
    }

    @Test
    fun testStatement() {
        val statement = Statement().statement(invoice, plays)
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

    @Test
    fun testStatementHtml() {
        val statement = Statement().statementHtml(invoice, plays)
        println(statement)
        val expect = """
        <h1> 청구내역 (고객명: BigCo)
         </h1><table> 
        <tr><th> 연극 </th> <th>좌석 수</th> <th>금액</th><tr><td> Hamlet: </td> <td> $650 </td> <td> 55석 </td></tr>
        <tr><td> As You Like It: </td> <td> $580 </td> <td> 35석 </td></tr>
        <tr><td> Othello: </td> <td> $500 </td> <td> 40석 </td></tr>
        </table>
        총액: $1,730.00
        적립 포인트: 47점
        """.trimIndent()
        assertEquals(expect, statement)
    }
}