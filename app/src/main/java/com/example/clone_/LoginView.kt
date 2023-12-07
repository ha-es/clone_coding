package com.example.clone_

interface LoginView {
    fun onLoginSuccess(code  :Int, result: com.example.clone_.Result)
    fun onLoginFailure()
}