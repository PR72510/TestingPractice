package com.perapps.testingpractice

import com.google.common.truth.Truth.*
import com.perapps.testingpractice.homework.RegistrationUtil
import org.junit.Test


class RegistrationUtilTest{

    @Test
    fun emptyUserNameReturnsFalse(){
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "1234",
            "1234"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun validUserNameCorrectlyRepeatedPasswordReturnsTrue(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Lester",
            "1234",
            "1234"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun userNameTakenReturnsFalse(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Nick",
            "1234",
            "1234"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun emptyPasswordReturnsFalse(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Lester",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun passwordRepeatedIncorrectlyReturnsFalse(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Lester",
            "1234",
            "hngfdf"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun passwordHasLessThanTwoDigitsReturnsFalse(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Lester",
            "password2",
            "password2"
        )
        assertThat(result).isFalse()
    }
}