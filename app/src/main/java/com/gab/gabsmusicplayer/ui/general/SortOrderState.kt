package com.gab.gabsmusicplayer.ui.general

data class SortOrderState(
    val order: SortOder = SortOder.Asc,
    val parameter: SortParameter = SortParameter.Date,
) {
    fun getInvertedOrder(): SortOrderState {
        return this.copy(
            order = when(order) {
                SortOder.Asc -> SortOder.Desc
                SortOder.Desc -> SortOder.Asc
            }
        )
    }
}

enum class SortOder {
    Asc, Desc;
}

enum class SortParameter(val orderBy: String) {
    Date("Дата"), Title("Название");
}