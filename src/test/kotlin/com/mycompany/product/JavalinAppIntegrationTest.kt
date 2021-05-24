package com.mycompany.product

import io.javalin.plugin.json.JavalinJson
import kong.unirest.HttpResponse
import kong.unirest.Unirest
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test


class JavalinAppIntegrationTest {

    private val app = JavalinApp()

    @Test
    fun `GET ALL to fetch all products`() {
        app.start(1234)
        val response: HttpResponse<String> = Unirest.get("http://localhost:1234/product").asString()

        assertThat(response.status).isEqualTo(200)
        val productListJson = JavalinJson.toJson(ProductController.products.values.toList())
        assertThat(response.body).isEqualTo(productListJson)
        app.stop()
    }

    @Test
    fun `GET specific product`() {
        app.start(1234)
        val productId = 1L
        val response: HttpResponse<String> = Unirest.get("http://localhost:1234/product/$productId").asString()

        assertThat(response.status).isEqualTo(200)
        val productJson = JavalinJson.toJson(ProductController.products.getOrElse(productId, { assert(false) }))
        assertThat(response.body).isEqualTo(productJson)
        app.stop()
    }
}