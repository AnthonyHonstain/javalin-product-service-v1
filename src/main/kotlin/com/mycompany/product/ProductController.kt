package com.mycompany.product

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context

object ProductController {

    var products = mutableListOf(
            Product(1, "SKU-01"),
            Product(2, "SKU-02"),
            Product(3, "SKU-03"),
    )

    fun create(ctx: Context) {
        val productname = ctx.queryParam("productname")
        if (productname == null || productname.length < 5) {
            throw BadRequestResponse()
        } else {
            products.add(Product(products.size.toLong() + 1,productname))
            ctx.status(201)
        }
    }

    fun getAll(ctx: Context) {
        ctx.json(products)
    }
}