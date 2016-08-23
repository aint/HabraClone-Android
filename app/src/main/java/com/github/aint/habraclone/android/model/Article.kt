package com.github.aint.habraclone.android.model

import java.io.Serializable

class Article : Serializable {
    var id: Long = 0
    var title: String? = null
    var preview: String? = null
    var body: String? = null
    var rating: Int = 0
    var keywords: String? = null
    var views: Int = 0
    var favorites: Int = 0
    var creationDate: Long = 0
    var authorUsername: String? = null
    var hubName: String? = null
    var commentCount: Int = 0
}
