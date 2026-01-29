package com.itd.app.features.search.ui.mappers

import com.itd.app.features.common.api.models.HashtagPreview
import com.itd.app.features.feed.ui.list.mappers.formatThousand
import com.itd.app.features.search.ui.HashtagVO

fun HashtagPreview.toVO(index: Int): HashtagVO {
    return HashtagVO(
        id = id,
        index = (index + 1).toString(),
        name = "#$name",
        postsCount = "${postsCount.formatThousand()} постов"
    )
}