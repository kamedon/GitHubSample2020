package jp.classmethod.githubapp.util.extenstion

import jp.classmethod.githubapp.common.LoadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.toLoadingState(): Flow<LoadState<T>> =
    map<T, LoadState<T>> {
        LoadState.Loaded(it)
    }.onStart {
        emit(LoadState.Loading)
    }.catch {
        emit(LoadState.Error(it))
    }
