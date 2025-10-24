package com.gab.core_music_loading.impl.data.room.entities.database

import com.gab.core_music_loading.R
import java.util.concurrent.atomic.AtomicInteger

internal object PictureSelector {
    private val pics = listOf(
        R.drawable.gpic_1,
        R.drawable.gpic_2,
        R.drawable.gpic_3,
        R.drawable.gpic_4,
        R.drawable.gpic_5,
        R.drawable.gpic_6,
        R.drawable.gpic_7,
        R.drawable.gpic_8,
        R.drawable.gpic_9,
        R.drawable.gpic_10,
        R.drawable.gpic_11,
        R.drawable.gpic_12,
        R.drawable.gpic_13,
        R.drawable.gpic_14,
        R.drawable.gpic_15,
        R.drawable.gpic_16,
        R.drawable.gpic_17,
        R.drawable.gpic_18,
        R.drawable.gpic_19,
        R.drawable.gpic_20,
        R.drawable.gpic_21,
        R.drawable.gpic_22,
        R.drawable.gpic_23,
        R.drawable.gpic_24,
        R.drawable.gpic_25,
        R.drawable.gpic_26,
        R.drawable.gpic_27,
        R.drawable.gpic_28,
        R.drawable.gpic_29,
        R.drawable.gpic_30,
        R.drawable.gpic_31,
        R.drawable.gpic_32,
        R.drawable.gpic_33,
        R.drawable.gpic_34,
        R.drawable.gpic_35,
        R.drawable.gpic_36,
        R.drawable.gpic_39,
        R.drawable.gpic_40,
        R.drawable.gpic_41,
        R.drawable.gpic_42,
        R.drawable.gpic_43,
        R.drawable.gpic_44,
        R.drawable.gpic_45,
        R.drawable.gpic_46,
        R.drawable.gpic_47,
        R.drawable.gpic_48,
        R.drawable.gpic_49,
        R.drawable.gpic_50,
        R.drawable.gpic_51
    )

    private var picSelectedCnt = AtomicInteger(0)
    internal fun getPic() =
        pics[(picSelectedCnt.getAndIncrement()) % pics.size]
}