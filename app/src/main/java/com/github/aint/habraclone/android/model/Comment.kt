package com.github.aint.habraclone.android.model

import java.io.Serializable

class Comment : Serializable {
    var id: Long = 0
    var body: String? = null
    var rating: Int = 0
    var creationDate: Long = 0
    var author: String? = null
    var articleId: Long = 0
}
