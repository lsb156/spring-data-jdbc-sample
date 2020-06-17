package com.ssabae.springdatajdbc.sample.label

import org.springframework.context.ApplicationListener
import org.springframework.core.Ordered
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent
import java.util.*

class Label (
    var id: UUID?,
    var isNew: Boolean
) {
    companion object {
        class LabelAfterSaveEventListener() : ApplicationListener<AfterSaveEvent<*>>, Ordered {
            override fun onApplicationEvent(event: AfterSaveEvent<*>) {
                val entity = event.entity
                if (entity is Label) {
                    entity.isNew = false
                }
            }

            override fun getOrder(): Int {
                return Ordered.HIGHEST_PRECEDENCE
            }
        }


    }
}