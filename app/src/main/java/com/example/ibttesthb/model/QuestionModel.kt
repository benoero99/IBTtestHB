package com.example.ibttesthb.model

data class QuestionModel(
    var tags: List<String>,
    var owner: Owner,
    var is_answered: Boolean,
    var view_count: Int,
    var answer_count: Int,
    var score: Int,
    var last_activity_date: Int,
    var creation_date: Int,
    var question_id: Int,
    var link: String,
    var title: String
)

data class Owner(
    var account_id: Int,
    var reputation: Int,
    var user_id: Int,
    var user_type: String,
    var profile_image: String,
    var display_name: String,
    var link: String,
)
