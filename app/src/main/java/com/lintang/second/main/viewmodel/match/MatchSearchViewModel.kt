package com.lintang.second.main.viewmodel.match

import android.app.Application
import android.widget.Toast
import com.lintang.second.base.BaseViewModel
import com.lintang.second.base.modular.model.match.SingleMatch
import com.lintang.second.base.modular.source.DataSource
import com.lintang.second.base.modular.source.Repository
import com.lintang.second.base.modular.util.SingleLiveEvent

class MatchSearchViewModel(val context: Application, private val repository: Repository) :
    BaseViewModel(context) {
    var matchListLive = SingleLiveEvent<List<SingleMatch>>()


    fun getSearchMatch(event: String) {
        repository.getSearchMatch(event, object : DataSource.GetRemoteCallback<List<SingleMatch>> {
            override fun onEmpty() {
                eventIsEmpty.postValue(true)
            }

            override fun onShowProgressDialog() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgressDialog() {
                eventShowProgress.postValue(false)
            }

            override fun onSuccess(data: List<SingleMatch>) {
                if (data.isEmpty()) {
                    onEmpty()
                } else {
                    matchListLive.postValue(data)
                }
            }

            override fun onFinish() {

            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                Toast.makeText(context, errorMessage ?: "Unknown", Toast.LENGTH_LONG).show()
            }
        })
    }

}