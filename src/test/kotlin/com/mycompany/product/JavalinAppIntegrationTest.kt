package com.mycompany.product

import io.javalin.plugin.json.JavalinJson
import kong.unirest.HttpResponse
import kong.unirest.Unirest
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test


class JavalinAppIntegrationTest {

    private val app = JavalinApp()
    private val usersJson = JavalinJson.toJson(ProductController.products)

    @Test
    fun `GET to fetch users returns list of users`() {
        app.start(1234)
        val response: HttpResponse<String> = Unirest.get("http://localhost:1234/products").asString()
        assertThat(response.status).isEqualTo(200)
        assertThat(response.body).isEqualTo(usersJson)
        app.stop()
    }
}