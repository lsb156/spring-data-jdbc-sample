package com.ssabae.springdatajdbc.sample.support

import javax.validation.constraints.NotNull

class EncryptString(
    @NotNull
    val value: String
)