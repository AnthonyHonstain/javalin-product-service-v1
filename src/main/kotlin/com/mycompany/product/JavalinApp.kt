package com.mycompany.product

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post

import io.javalin.plugin.metrics.MicrometerPlugin
import io.micrometer.core.instrument.Clock
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jetty.TimedHandler
import io.micrometer.core.instrument.util.HierarchicalNameMapper
import io.micrometer.graphite.GraphiteConfig
import io.micrometer.graphite.GraphiteMeterRegistry
import java.time.Duration


class JavalinApp {
    val graphiteConfig: GraphiteConfig = object : GraphiteConfig {
        override fun host(): String {
            return "localhost"
        }

        override fun get(p0: String): String? {
            return null
        }

        override fun step(): Duration {
            return Duration.ofSeconds(5)
        }
    }

    val graphiteRegistry = GraphiteMeterRegistry(graphiteConfig, Clock.SYSTEM, HierarchicalNameMapper.DEFAULT)

    val app = Javalin.create { config ->
        config.registerPlugin(MicrometerPlugin(graphiteRegistry))
        //config.enableDevLogging()
        //config.requestLogger { ctx, ms -> log.error("${ctx.url()} time:$ms")}
    } .routes {
        get("/products", ProductController::getAll)
        post("products", ProductController::create)
    }

    fun start(port: Int) {
        JvmMemoryMetrics().bindTo(graphiteRegistry)
        TimedHandler(graphiteRegistry, emptyList())
        this.app.start(port)
    }

    fun stop() {
        app.stop()
    }
}
