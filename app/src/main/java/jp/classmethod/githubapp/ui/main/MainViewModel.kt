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

    fun fetch() {
        viewModelScope.launch(Dispatchers.Main) {
            useCase.user(nameEdit.value ?: "").flowOn(Dispatchers.IO).toLoadingState().collect {
                user.value = it
            }
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

