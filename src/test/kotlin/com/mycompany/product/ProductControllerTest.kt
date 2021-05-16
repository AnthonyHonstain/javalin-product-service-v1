package com.mycompany.product

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test


class ProductControllerTest {

    private val ctx = mockk<Context>(relaxed = true)

    @Test
    fun `GET to root gives 200`() {
        every { ctx.queryParam("productname") } returns "Roland"
        ProductController.create(ctx) // the handler we're testing
        verify { ctx.status(201) }
    }

    @Test
    fun `POST to create users throws for invalid username`() {
        every { ctx.queryParam("productname") } returns null
        assertThrows(BadRequestResponse::class.java) { ProductController.create(ctx) } // the handler we're testing
    }
}