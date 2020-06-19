package com.ssabae.springdatajdbc.sample.support

import org.springframework.data.jdbc.core.mapping.AggregateReference
import javax.validation.valueextraction.ValueExtractor
import javax.validation.valueextraction.ValueExtractor.ValueReceiver

// 코틀린에서 동작하지 않는 이슈로 자바 파일로 따로 만듦
// ValueExtractor<AggregateReference<*, @ExtractedValue *>> 형식으로 되어야 하는데
// kotlin 문법상 star-projection앞에 annotation이 들어가지 않음
class AggregateReferenceValueExtractor_Kt : ValueExtractor<AggregateReference<*, *>> {
    override fun extractValues(originalValue: AggregateReference<*, *>, receiver: ValueReceiver) {
        receiver.value("id", originalValue.id)
    }
}
