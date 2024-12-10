package com.divineengine.statussaverprofromwa.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.divineengine.statussaverprofromwa.data.StatusRepo
import com.divineengine.statussaverprofromwa.viewmodels.StatusViewModel

class StatusViewModelFactory(private val repo: StatusRepo):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatusViewModel(repo) as T
    }
}