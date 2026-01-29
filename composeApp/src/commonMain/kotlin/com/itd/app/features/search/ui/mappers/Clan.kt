package com.itd.app.features.search.ui.mappers

import com.itd.app.features.common.api.models.ClanPreview
import com.itd.app.features.search.ui.ClanVO

fun ClanPreview.toVO(index: Int): ClanVO {
    return ClanVO(
        index = (index + 1).toString(),
        avatar = avatar,
        memberCount = membersCount.toString(),
        highlighted = index <= 2
    )
}