package com.gab.model_module.usecasesImpl

import com.gab.core_music_loading.api.data.MusicLoadingRepository
import com.gab.model_module.usecases.UpdateUseCase
import javax.inject.Inject

public class UpdateUseCaseImpl @Inject constructor(private val repository: MusicLoadingRepository) :
    UpdateUseCase {
    override suspend operator fun invoke() {
        repository.update()
    }
}