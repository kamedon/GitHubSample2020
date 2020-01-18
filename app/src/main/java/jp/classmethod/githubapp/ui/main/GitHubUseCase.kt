package jp.classmethod.githubapp.ui.main

import jp.classmethod.githubapp.repository.IGitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IGitHubUseCase {
    suspend fun user(name: String): Flow<UserPresentModel>
}

class GitHubUseCase(private val repository: IGitHubRepository) : IGitHubUseCase {

    override suspend fun user(name: String): Flow<UserPresentModel> = flow {

        val response = repository.user(name)

        emit(
            if (response != null) {
                UserPresentModel(response.id, response.reposUrl)
            } else {
                UserPresentModel.NONE
            }
        )
    }


}