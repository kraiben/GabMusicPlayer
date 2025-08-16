package com.gab.model_module.usecases

public interface CheckIfPlaylistUniqueForEditCase {
    public suspend operator fun invoke(title: String): Boolean}