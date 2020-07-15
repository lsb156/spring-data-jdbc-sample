package com.ssabae.springdatajdbc.sample.support

import org.springframework.data.jdbc.core.mapping.AggregateReference
import javax.validation.valueextraction.ValueExtractor
import javax.validation.valueextraction.ValueExtractor.ValueReceiver

class AggregateReferenceValueExtractor : ValueExtractor<AggregateReference<*, *>> {
    override fun extractValues(
        originalValue: AggregateReference<*, *>,
        receiver: ValueReceiver
    ) {
        receiver.value("id", originalValue.id)
    }
}