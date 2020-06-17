package com.ssabae.springdatajdbc.sample.support

import org.springframework.data.jdbc.core.mapping.AggregateReference
import javax.validation.valueextraction.ExtractedValue
import javax.validation.valueextraction.ValueExtractor
import javax.validation.valueextraction.ValueExtractor.ValueReceiver

class AggregateReferenceValueExtractor :
    ValueExtractor<AggregateReference<*, @ExtractedValue Any>> {
    override fun extractValues(originalValue: AggregateReference<*, Any>, receiver: ValueReceiver) {
        receiver.value("id", originalValue.id)
    }
}
