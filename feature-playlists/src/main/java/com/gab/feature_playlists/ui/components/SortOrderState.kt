package com.gab.feature_playlists.ui.components

internal data class SortOrderState(
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

internal enum class SortOder {
    Asc, Desc;
}

internal enum class SortParameter(val orderBy: String) {
    Date("Дата"), Title("Название");
}