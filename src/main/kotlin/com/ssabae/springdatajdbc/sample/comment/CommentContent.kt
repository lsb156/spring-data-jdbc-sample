package com.ssabae.springdatajdbc.sample.comment

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class CommentContent (
    val body: String,

    @NotNull
    @Size(max = 20)
    val mimeType: String
)