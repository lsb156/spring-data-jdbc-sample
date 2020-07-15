package com.ssabae.springdatajdbc.sample.issue

import com.ssabae.springdatajdbc.sample.label.Label
import org.springframework.data.jdbc.core.mapping.AggregateReference
import java.time.Instant
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent

class IssueAttachedLabel (

    val labelId: AggregateReference<Label, UUID>,

    @NotNull
    @PastOrPresent
    val attachedAt: Instant
)