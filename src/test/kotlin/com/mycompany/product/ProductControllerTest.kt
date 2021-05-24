package com.mycompany.product

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context

import org.mockito.Mockito

//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify


class ProductControllerTest {

    //private val ctx = mockk<Context>(relaxed = true)
    private val ctx = Mockito.mock(Context::class.java)

    @Test
    fun `POST to CREATE product gives 200`() {
        //val expectedJson: String = JavalinJson.toJson(Product(1, "SKU-01", null, null))
        //val expectedJson: String =  "{\"productId\":1,\"sku\":\"FOO\",\"barcode\":null,\"taxCode\":null,\"foo\": \"bar\"}"
        //every { ctx.body<Product>() } returns Product(1, "SKU-01", null, null)
        //every { ctx.bodyAsClass(Product::class.java) } returns Product(1, "SKU-01", null, null)
        //val productRaw: String = "{\"productId\":1,\"sku\":\"FOO\",\"barcode\":null,\"taxCode\":null,\"foo\": \"bar\"}"

        //Mockito.`when`(ctx.body()).thenReturn(productRaw)
        //Mockito.`when`(ctx.bodyAsBytes()).thenReturn(productRaw.toByteArray())
        Mockito.`when`(ctx.body<Product>()).thenReturn(Product(1, "SKU-01", null, null))

        ProductController.create(ctx)
        verify(ctx).status(201)
    }

    @Test
    fun `POST to create product throws for invalid sku`() {
        Mockito.`when`(ctx.body<Product>()).thenReturn(Product(1, "", null, null))

        assertThrows(BadRequestResponse::class.java) { ProductController.create(ctx) }
    }
}