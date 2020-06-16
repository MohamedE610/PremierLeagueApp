package com.example.premierleagueapp.core.presentation.modelwraper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.premierleagueapp.core.data.source.remote.rxerrorhandling.PremierLeagueException


class ObservableResource<T> : SingleLiveEvent<T>() {

    val error: SingleLiveEvent<PremierLeagueException> = ErrorLiveData()
    val loading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun observe(
        owner: LifecycleOwner, successObserver: Observer<T>,
        loadingObserver: Observer<Boolean>? = null,
        commonErrorObserver: Observer<PremierLeagueException>,
        httpErrorConsumer: Observer<PremierLeagueException>? = null,
        networkErrorConsumer: Observer<PremierLeagueException>? = null,
        unExpectedErrorConsumer: Observer<PremierLeagueException>? = null,
        serverDownErrorConsumer: Observer<PremierLeagueException>? = null,
        timeOutErrorConsumer: Observer<PremierLeagueException>? = null,
        unAuthorizedErrorConsumer: Observer<PremierLeagueException>? = null
    ) {
        super.observe(owner, successObserver)
        loadingObserver?.let { loading.observe(owner, it) }
        (error as ErrorLiveData).observe(
            owner,
            commonErrorObserver,
            httpErrorConsumer,
            networkErrorConsumer,
            unExpectedErrorConsumer,
            serverDownErrorConsumer,
            timeOutErrorConsumer,
            unAuthorizedErrorConsumer
        )
    }


    class ErrorLiveData : SingleLiveEvent<PremierLeagueException>() {
        private var ownerRef: LifecycleOwner? = null
        private var httpErrorConsumer: Observer<PremierLeagueException>? = null
        private var networkErrorConsumer: Observer<PremierLeagueException>? = null
        private var unExpectedErrorConsumer: Observer<PremierLeagueException>? = null
        private var commonErrorConsumer: Observer<PremierLeagueException>? = null

        private var serverDownErrorConsumer: Observer<PremierLeagueException>? = null
        private var timeOutErrorConsumer: Observer<PremierLeagueException>? = null
        private var unAuthorizedErrorConsumer: Observer<PremierLeagueException>? = null

        override fun setValue(value: PremierLeagueException?) {
            ownerRef?.also {
                removeObservers(it)
                value?.let { vale -> addProperObserver(vale) }
            }
            super.setValue(value)
        }

        override fun postValue(value: PremierLeagueException) {
            ownerRef?.also {
                removeObservers(it)
                addProperObserver(value)
                super.postValue(value)
            }

        }

        private fun addProperObserver(value: PremierLeagueException) {
            when (value.kind) {
                PremierLeagueException.Kind.NETWORK -> networkErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                PremierLeagueException.Kind.HTTP -> httpErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                PremierLeagueException.Kind.UNEXPECTED -> unExpectedErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                PremierLeagueException.Kind.SERVER_DOWN -> serverDownErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                PremierLeagueException.Kind.TIME_OUT -> timeOutErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                PremierLeagueException.Kind.UNAUTHORIZED -> unAuthorizedErrorConsumer?.let {
                    observe(
                        ownerRef!!,
                        it
                    )
                }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                else -> {
                }
            }
        }


        fun observe(
            owner: LifecycleOwner, commonErrorConsumer: Observer<PremierLeagueException>,
            httpErrorConsumer: Observer<PremierLeagueException>? = null,
            networkErrorConsumer: Observer<PremierLeagueException>? = null,
            unExpectedErrorConsumer: Observer<PremierLeagueException>? = null,

            serverDownErrorConsumer: Observer<PremierLeagueException>? = null,
            timeOutErrorConsumer: Observer<PremierLeagueException>? = null,
            unAuthorizedErrorConsumer: Observer<PremierLeagueException>? = null
        ) {
            super.observe(owner, commonErrorConsumer)
            this.ownerRef = owner
            this.commonErrorConsumer = commonErrorConsumer
            this.httpErrorConsumer = httpErrorConsumer
            this.networkErrorConsumer = networkErrorConsumer
            this.unExpectedErrorConsumer = unExpectedErrorConsumer
            this.serverDownErrorConsumer = serverDownErrorConsumer
            this.timeOutErrorConsumer = timeOutErrorConsumer
            this.unAuthorizedErrorConsumer = unAuthorizedErrorConsumer
        }
    }
}