package com.example.playlistmaker1.sharing.domain.api

import com.example.playlistmaker1.sharing.domain.models.EmailData

interface ExternalNavigator {
    fun share(text: String)
    fun openLink(termsLink: String)
    fun openEmail(supportEmail: EmailData)
}