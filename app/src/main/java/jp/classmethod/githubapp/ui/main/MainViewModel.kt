package jp.classmethod.githubapp.ui.main

import androidx.lifecycle.*
import jp.classmethod.githubapp.common.LoadState
import jp.classmethod.githubapp.util.extenstion.toLoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
class MainViewModel(private val useCase: IGitHubUseCase) : ViewModel() {

    val nameEdit = MutableLiveData<String>()
    val user: MutableLiveData<LoadState<UserPresentModel>> = MutableLiveData()

    val feed: LiveData<LoadState<FeedPresentModel>> = liveData {
        emitSource(useCase.feed().toLoadingState().asLiveData())
    }

    fun fetch() {
        viewModelScope.launch(Dispatchers.Main) {
            useCase.user(nameEdit.value ?: "").toLoadingState().collect {
                user.value = it
            }
        }
    }

    val feedUrl = feed.map {
        when (it) {
            is LoadState.Loaded -> {
                it.value.timelineUrl
            }
            else -> ""
        }
    }

    val id = user.map {
        when (it) {
            is LoadState.Loaded -> {
                it.value.id
            }
            else -> ""
        }
    }

    val repoUrl = user.map {
        when (it) {
            is LoadState.Loaded -> {
                it.value.reposUrl
            }
            else -> ""
        }
    }

    val loading = user.map {
        it is LoadState.Loading
    }


}

