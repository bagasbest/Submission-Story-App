package com.project.dicodingplayground.submission_story_app.view.story

import java.io.File

data class StoryAddPayload(
    var photo: File? = null,
    var description: String? = null,
    var lat: Double? = null,
    var lon: Double? = null,

)
