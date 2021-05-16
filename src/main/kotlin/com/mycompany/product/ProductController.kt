package com.mycompany.product

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context

object ProductController {

    var products = mutableListOf("User1", "User2", "User3")

    fun create(ctx: Context) {
        val productname = ctx.queryParam("productname")
        if (productname == null || productname.length < 5) {
            throw BadRequestResponse()
        } else {
            products.add(productname)
            ctx.status(201)
        }
    }

    fun getAll(ctx: Context) {
        ctx.json(products)
    }
}