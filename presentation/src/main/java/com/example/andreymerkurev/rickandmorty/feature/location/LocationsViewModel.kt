package com.example.andreymerkurev.rickandmorty.feature.location

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.anderymerkurev.domain.entities.Characters
import com.example.anderymerkurev.domain.entities.Locations
import com.example.anderymerkurev.domain.entities.RequestResult
import com.example.anderymerkurev.domain.interactors.ILocationsInteractor
import com.example.andreymerkurev.rickandmorty.di.Injector
import java.util.ArrayList
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val locationsInteractor: ILocationsInteractor
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 20
    }

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isError: LiveData<Boolean> = _isError

    private val _listParams = MutableLiveData<ArrayList<String>>().apply {
        value = arrayListOf("", "", "")
    }

    val listLocations = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        locationPagingSource
    }.flow.cachedIn(viewModelScope)

    private val locationPagingSource = object : PagingSource<Int, Locations>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Locations> {
            _isLoading.value = true
            _isError.value = false
            val currentLoadingPageKey = params.key ?: 1
            val prevKey =
                if (currentLoadingPageKey == 1)
                    null
                else
                    currentLoadingPageKey - 1

            val response = if (_listParams.value!![2] == "1") {
                locationsInteractor.getLocalPagingData(_listParams.value!![0])
            } else {
                locationsInteractor.getPagingData(currentLoadingPageKey, _listParams.value!!)
            }

            _isLoading.value = false
            return when (response) {
                is RequestResult.Success -> {
                    _isError.value = false
                    val pages = response.data.info.count / PAGE_SIZE
                    val p = if (pages < 1) 1 else pages
                    LoadResult.Page(
                        data = response.data.results,
                        prevKey = prevKey,
                        nextKey = if (currentLoadingPageKey.plus(1) <= p)
                            currentLoadingPageKey.plus(1)
                        else
                            null
                    )
                }
                is RequestResult.Error -> {
                    _isError.value = true
                    LoadResult.Page(
                        data = listOf(),
                        prevKey = prevKey,
                        nextKey = null
                    )
                }
            }
        }

        override fun getRefreshKey(state: PagingState<Int, Locations>): Int {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }!!
        }
    }

    fun setRequestParams(listParams: ArrayList<String>) {
        _listParams.value = listParams
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearLocationsComponent()
    }
}