package com.itd.app.features.notifications.data

import com.itd.app.core.ktor.NetworkConstants
import com.itd.app.features.notifications.api.NotificationRepository
import com.itd.app.features.notifications.api.models.Notification
import com.itd.app.features.notifications.data.dto.GetNotificationsResponse
import com.itd.app.features.notifications.data.mappers.toDomain
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NotificationRepositoryImpl(
    private val httpClient: HttpClient
) : NotificationRepository {
    override suspend fun getNotifications(offset: Int): Result<List<Notification>> {
        return runCatching {
            httpClient.get("${NetworkConstants.BASE_URL}/notifications") {
                parameter("offset", offset)
                parameter("limit", PAGE_LIMIT)
            }.body<GetNotificationsResponse>().notifications.map { it.toDomain() }
        }
    }

    companion object {
        private const val PAGE_LIMIT = 20
    }
}