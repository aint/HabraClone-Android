package com.github.aint.habraclone.android.model

class User {
    var id: Long = 0
    var username: String? = null
    var email: String? = null
    var fullName: String? = null
    var password: String? = null
    var about: String? = null
    var location: String? = null
    var rating: Int = 0
    var isAdmin: Boolean = false
    var isEnabled: Boolean = false
    var birthday: Long? = null
    var lastLoginTime: Long? = null
    var registrationDate: Long? = null
    var banExpirationDate: Long? = null
    var language: String? = null
    var articlesCount: Int = 0
    var commentsCount: Int = 0
    var favoritesCount: Int = 0
}
