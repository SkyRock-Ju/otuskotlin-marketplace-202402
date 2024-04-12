@file:Suppress("unused")

package ru.otus.otuskotlin.m1l5

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// Реализуйте dsl для составления sql запроса, чтобы все тесты стали зелеными.

class Hw1Sql {
    private fun checkSQL(expected: String, sql: SqlSelectBuilder) {
        assertEquals(expected, sql.build())
    }

    @Test
    fun `simple select all from table`() {
        val expected = "select * from table"

        val real = query {
            from("table")
        }


        checkSQL(expected, real)

    }

    private fun query(function: SqlSelectBuilder.() -> Unit): SqlSelectBuilder {
        return SqlSelectBuilder().apply(function)
    }

    @Test
    fun `check that select can't be used without table`() {
        assertFailsWith<Exception> {
            query {
                select("col_a")
            }.build()
        }
    }

    @Test
    fun `select certain columns from table`() {
        val expected = "select col_a, col_b from table"

        val real = query {
            select("col_a", "col_b")
            from("table")
        }

        checkSQL(expected, real)
    }

    @Test
    fun `select certain columns from table 1`() {
        val expected = "select col_a, col_b from table"

        val real = query {
            select("col_a", "col_b")
            from("table")
        }

        checkSQL(expected, real)
    }

    /**
     * __eq__ is "equals" function. Must be one of char:
     *  - for strings - "="
     *  - for numbers - "="
     *  - for null - "is"
     */
    @Test
    fun `select with complex where condition with one condition`() {
        val expected = "select * from table where col_a = 'id'"

        val real = query {
            from("table")
            where { "col_a" eq "id" }
        }

        checkSQL(expected, real)
    }

    /**
     * __nonEq__ is "non equals" function. Must be one of chars:
     *  - for strings - "!="
     *  - for numbers - "!="
     *  - for null - "!is"
     */
    @Test
    fun `select with complex where condition with two conditions`() {
        val expected = "select * from table where col_a != 0"

        val real = query {
            from("table")
            where {
                "col_a" nonEq 0
            }
        }

        checkSQL(expected, real)
    }

    @Test
    fun `when 'or' conditions are specified then they are respected`() {
        val expected = "select * from table where (col_a = 4 or col_b !is null)"

        val real = query {
            from("table")
            where {
                or {
                    "col_a" eq 4
                    "col_b" nonEq null
                }
            }
        }

        checkSQL(expected, real)
    }

    private fun or(function: () -> String): String {
        print(function)
        return function.toString()
    }
}

private infix fun String.eq(s: String): String {
    return "$this = '$s'"
}

private infix fun String.eq(s: Number): String {
    return "$this = '$s'"
}

private infix fun String.eq(s: Nothing?): String {
    return "$this is '$s'"
}

private infix fun String.nonEq(s: String): String {
    return "$this != '$s'"
}

private infix fun String.nonEq(s: Number): String {
    return "$this != $s"
}

private infix fun String.nonEq(s: Nothing?): String {
    return "$this !is '$s'"
}

class SqlSelectBuilder(
    private var fromTable: String? = null,
    private var database: String? = "*",
    private var where: String? = "",
    private var or: List<String> = listOf(),
) {

//    fun or(init: SqlSelectBuilder.() -> Unit) {
//        val (first, second) = SqlSelectBuilder().apply(init).build()
//        or = listOf(first, second)
//
//    }

    fun from(table: String) {
        fromTable = table
    }

    fun select(data: String) {
        database = data
    }

    fun select(data1: String, data2: String) {
        database = "$data1, $data2"
    }

    fun where(block: () -> Any) {
        where = block().toString()
    }

    fun build(): String {
        if (fromTable == null) {
            throw IllegalStateException("FROM clause must be specified")
        }
        return "select $database from $fromTable where $where$or"
    }
}