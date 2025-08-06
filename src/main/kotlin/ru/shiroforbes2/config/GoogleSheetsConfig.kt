package ru.shiroforbes2.config

import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.sheets.v4.Sheets
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GoogleSheetsConfig {
  @Value("\${shiroforbes.app.google.serviceAccountKeyFile}")
  private lateinit var serviceAccountKeyFile: String

  @Bean
  fun googleCredentials(): GoogleCredentials {
    val credentialsStream =
      GoogleSheetsConfig::class.java.classLoader.getResourceAsStream(serviceAccountKeyFile)
    return GoogleCredentials
      .fromStream(credentialsStream)
      .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets", DriveScopes.DRIVE))
  }

  @Bean
  fun sheetsClient(credentials: GoogleCredentials): Sheets =
    Sheets
      .Builder(
        com.google.api.client.http.javanet
          .NetHttpTransport(),
        com.google.api.client.json.gson.GsonFactory
          .getDefaultInstance(),
        HttpCredentialsAdapter(credentials),
      ).setApplicationName("GoogleSheetsService")
      .build()

  @Bean
  fun driveClient(credentials: GoogleCredentials): Drive =
    Drive
      .Builder(
        com.google.api.client.http.javanet
          .NetHttpTransport(),
        com.google.api.client.json.gson.GsonFactory
          .getDefaultInstance(),
        HttpCredentialsAdapter(credentials),
      ).setApplicationName("GoogleDriveService")
      .build()
}
