package com.mycompany.product

import io.javalin.plugin.json.JavalinJson
import kong.unirest.HttpResponse
import kong.unirest.Unirest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test


class JavalinAppIntegrationTest {

    private val app = JavalinApp()

    private val PRODUCT_01 = Product(1, "SKU-01", null, null)
    private val PRODUCT_02 = Product(2, "SKU-02", null, null)

    @BeforeEach
    fun beforeEach() {
        ProductController.products.clear()
    }

    @Test
    fun `GET ALL to fetch all products`() {
        ProductController.products[PRODUCT_01.productId] = PRODUCT_01
        ProductController.products[PRODUCT_02.productId] = PRODUCT_02
        app.start(1234)
        val response: HttpResponse<String> = Unirest.get("http://localhost:1234/product").asString()

        assertThat(response.status).isEqualTo(200)
        val productListJson = JavalinJson.toJson(ProductController.products.values.toList())
        assertThat(response.body).isEqualTo(productListJson)
        app.stop()
    }

    @Test
    fun `GET specific product`() {
        ProductController.products[PRODUCT_01.productId] = PRODUCT_01
        app.start(1235)
        val response: HttpResponse<String> = Unirest.get("http://localhost:1235/product/${PRODUCT_01.productId}").asString()

        assertThat(response.status).isEqualTo(200)
        assertThat(response.body).isEqualTo(JavalinJson.toJson(PRODUCT_01))
        app.stop()
    }

    @Test
    fun `POST to create product`() {
        app.start(1236)
        val body: String = JavalinJson.toJson(PRODUCT_01)
        val response: HttpResponse<String> = Unirest.post("http://localhost:1236/product/").body(body).asString()

        assertThat(response.status).isEqualTo(201)
        assertThat(response.body).isEqualTo(JavalinJson.toJson(PRODUCT_01))
        app.stop()
    }

    @Test
    fun `PUT to update product`() {
        ProductController.products[1L] = PRODUCT_01
        app.start(1237)
        val updatedProduct = Product(1, "SKU-01", "updated_barcode", "updated_taxCode")
        val body: String = JavalinJson.toJson(updatedProduct)
        val response: HttpResponse<String> = Unirest.put("http://localhost:1237/product/${PRODUCT_01.productId}").body(body).asString()

        assertThat(response.status).isEqualTo(200)
        assertThat(response.body).isEqualTo(JavalinJson.toJson(updatedProduct))
        app.stop()
    }
}