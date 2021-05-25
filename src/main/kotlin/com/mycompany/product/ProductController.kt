package com.mycompany.product

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.plugin.json.JavalinJson
import org.valiktor.ConstraintViolationException
import org.valiktor.functions.isEqualTo
import org.valiktor.functions.isNotEmpty
import org.valiktor.i18n.mapToMessage
import org.valiktor.validate
import java.util.*

object ProductController {

    val products = mutableMapOf(
            1L to Product(1, "SKU-01", null, null),
            2L to Product(2, "SKU-02", null, null),
            3L to Product(3, "SKU-03", null, null),
    )

    fun create(ctx: Context) {
        val newProduct: Product = ctx.body<Product>()
        try {
            validate(newProduct) {
                validate(Product::sku).isNotEmpty()
            }
        } catch (ex: ConstraintViolationException) {
            val msg = ex.constraintViolations.mapToMessage(baseName = "messages", locale = Locale.ENGLISH)
                    .map { it.property to it.message}
                    .toMap()
            throw BadRequestResponse(JavalinJson.toJson(msg))
        }

        products[newProduct.productId] = newProduct
        ctx.status(201)
        ctx.json(newProduct)
    }

    fun put(ctx: Context) {
        val productId = ctx.pathParam("id").toLong()
        val updatedProduct: Product = ctx.body<Product>()

        try {
            validate(updatedProduct) {
                validate(Product::productId).isEqualTo(productId)
                validate(Product::sku).isNotEmpty()
            }
        } catch (ex: ConstraintViolationException) {
            val msg = ex.constraintViolations.mapToMessage(baseName = "messages", locale = Locale.ENGLISH)
                    .map { it.property to it.message}
                    .toMap()
            throw BadRequestResponse(JavalinJson.toJson(msg))
        }

        products.getOrElse(updatedProduct.productId, {
            throw BadRequestResponse()
        })

        products[updatedProduct.productId] = updatedProduct
        ctx.json(updatedProduct)
    }

    fun getAll(ctx: Context) {
        ctx.json(products.values.toList())
    }

    fun get(ctx: Context) {
        val productId = ctx.pathParam("id").toLong()
        val result = products.getOrElse(productId, {
            throw BadRequestResponse()
        })
        ctx.json(result)
    }
}