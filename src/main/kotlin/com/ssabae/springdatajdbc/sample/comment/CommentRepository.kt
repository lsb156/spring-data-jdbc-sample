package com.ssabae.springdatajdbc.sample.comment

import org.springframework.data.repository.CrudRepository

interface CommentRepository : CrudRepository<Comment, Long>