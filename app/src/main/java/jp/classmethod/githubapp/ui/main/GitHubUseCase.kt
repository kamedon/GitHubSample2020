package jp.classmethod.githubapp.ui.main

import jp.classmethod.githubapp.repository.IGitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IGitHubUseCase {
    suspend fun user(name: String): Flow<UserPresentModel>
    suspend fun feed(): Flow<FeedPresentModel>

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

    override suspend fun feed(): Flow<FeedPresentModel> = flow {
        val response = repository.feed()

        emit(
            if (response != null) {
                FeedPresentModel(response.timelineUrl)
            } else {
                FeedPresentModel.NONE
            }
        )
    }


}