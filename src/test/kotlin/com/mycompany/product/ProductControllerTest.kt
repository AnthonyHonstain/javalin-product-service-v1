package com.mycompany.product

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify


class ProductControllerTest {

    private val ctx = Mockito.mock(Context::class.java)
    private val PRODUCT_01 = Product(1, "SKU-01", null, null)

    @BeforeEach
    fun beforeEach() {
        ProductController.products.clear()
    }

    @Test
    fun `GET for single product gives 200`() {
        ProductController.products[1L] = PRODUCT_01
        Mockito.`when`(ctx.pathParam("id")).thenReturn("1")

        ProductController.get(ctx)
        verify(ctx).json(PRODUCT_01)
    }

    @Test
    fun `POST to CREATE product gives 200`() {
        Mockito.`when`(ctx.body<Product>()).thenReturn(PRODUCT_01)

        ProductController.create(ctx)
        verify(ctx).status(201)
        assertThat(ProductController.products[1L]).isEqualTo(PRODUCT_01)
    }

    @Test
    fun `POST to create product throws for invalid sku`() {
        Mockito.`when`(ctx.body<Product>()).thenReturn(Product(1, "", null, null))

        assertThrows(BadRequestResponse::class.java) { ProductController.create(ctx) }
        assertThat(ProductController.products.containsKey(1L)).isFalse
    }

    @Test
    fun `PUT to UPDATE product gives 200`() {
        ProductController.products[1L] = Product(1, "SKU-01", null, null)
        val updatedProduct = Product(1, "SKU-01_update", "barcode", "taxcode")
        Mockito.`when`(ctx.pathParam("id")).thenReturn("1")
        Mockito.`when`(ctx.body<Product>()).thenReturn(updatedProduct)

        ProductController.put(ctx)
        verify(ctx).json(updatedProduct)
        assertThat(ProductController.products[1L]).isEqualTo(updatedProduct)
    }

    @Test
    fun `PUT to update product throws for invalid sku`() {
        ProductController.products[1L] = Product(1, "SKU-01", null, null)
        Mockito.`when`(ctx.pathParam("id")).thenReturn("1")
        Mockito.`when`(ctx.body<Product>()).thenReturn(Product(1, "", null, null))

        assertThrows(BadRequestResponse::class.java) { ProductController.put(ctx) }
        assertThat(ProductController.products[1L]).isEqualTo(PRODUCT_01)
    }
}