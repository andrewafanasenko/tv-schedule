package com.example.tvschedule.data.search.source.local

import com.example.tvschedule.data.show_details.api.model.ShowResponse


interface SearchLocalDataSource {

    val shows: HashMap<Long, ShowResponse>

}
