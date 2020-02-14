package com.ai.project.ailab.login

public interface ILogin {
    fun onSignInWithEmail(email: String, password: String)
    fun onSignInWithGoogle()
}