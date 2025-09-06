package com.gab.model_media_usecases.usecases_api

public interface ChangePositionUseCase {
    public suspend operator fun invoke(position: Long)
}