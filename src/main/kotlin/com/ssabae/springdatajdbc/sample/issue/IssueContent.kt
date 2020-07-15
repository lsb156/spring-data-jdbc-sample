package com.ssabae.springdatajdbc.sample.issue

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class IssueContent (
    val body: String,

    @NotNull
    @Size(max = 20)
    val mimeType: String
)