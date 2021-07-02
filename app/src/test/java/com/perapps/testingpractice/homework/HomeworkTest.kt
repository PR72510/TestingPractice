package com.perapps.testingpractice.homework

import com.google.common.truth.Truth.assertThat
import com.perapps.testingpractice.homework.Homework.checkBraces
import com.perapps.testingpractice.homework.Homework.fib
import org.junit.Test


class HomeworkTest {

    @Test
    fun enter0Returns0() {
        val result = fib(0)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun enter1Returns1() {
        val result = fib(1)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun trailingBracketMoreThanLeadingReturnFalse(){
        val result = checkBraces("(asdas))")
        assertThat(result).isFalse()
    }

    @Test
    fun trailingBracketLessThanLeadingReturnFalse(){
        val result = checkBraces("((asdas)")
        assertThat(result).isFalse()
    }

    @Test
    fun noLeadingBracesReturnFalse(){
        val result = checkBraces("asdas))")
        assertThat(result).isFalse()
    }

    @Test
    fun noTrailingBracesReturnFalse(){
        val result = checkBraces("(asdas")
        assertThat(result).isFalse()
    }

    @Test
    fun equalNoOfLeadingAndTrailingBracesReturnTrue(){
        val result = checkBraces("((asdas))")
        assertThat(result).isTrue()
    }
}