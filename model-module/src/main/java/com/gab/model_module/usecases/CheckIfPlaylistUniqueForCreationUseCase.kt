package com.gab.model_module.usecases

public interface CheckIfPlaylistUniqueForCreationUseCase {
    public suspend operator fun invoke(title: String): Boolean}