package com.example.tvschedule.data.search.source.local

import com.example.tvschedule.data.show_details.api.model.ShowResponse
import javax.inject.Inject


class SearchLocalDataSourceImpl @Inject constructor() : SearchLocalDataSource {

    override val shows: HashMap<Long, ShowResponse> = hashMapOf()

}
