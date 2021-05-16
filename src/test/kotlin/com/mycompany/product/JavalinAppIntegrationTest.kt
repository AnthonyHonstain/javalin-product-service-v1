package com.mycompany.product

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.plugin.json.JavalinJson
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kong.unirest.HttpResponse
import kong.unirest.Unirest
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test


class JavalinAppIntegrationTest {

    private val app = JavalinApp()
    private val usersJson = JavalinJson.toJson(UserController.users)


    @Test
    fun `GET to fetch users returns list of users`() {
        app.start(1234)
        val response: HttpResponse<String> = Unirest.get("http://localhost:1234/users").asString()
        assertThat(response.status).isEqualTo(200)
        assertThat(response.body).isEqualTo(usersJson)
        app.stop()
    }
}