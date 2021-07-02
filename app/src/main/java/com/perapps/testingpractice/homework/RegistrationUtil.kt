package com.perapps.testingpractice.homework


object RegistrationUtil {

    private val existingUsers = listOf("Nick", "Ronnie")

    /**
     *  input is not valid if
     *    -> username/password is empty
     *    -> username already taken
     *    -> confirm password and password does`nt match
     *    -> password contains less than two digits
     */

    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if (userName.isBlank() || password.isBlank() || confirmedPassword.isBlank()) {
            return false
        }
        if (userName in existingUsers) {
            return false
        }
        if (password != confirmedPassword) {
            return false
        }
        if (password.count { it.isDigit() } < 2) {
            return false
        }
        return true
    }
}