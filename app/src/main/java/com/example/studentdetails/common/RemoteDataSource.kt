package common

import org.jetbrains.annotations.NotNull

typealias K = Any

interface RemoteDataSource
{
    interface DataLoadedCallback
    {
        fun onLoadingSuccess(data : K)
        fun onLoadingError(errorCode : Int, errorMsg : String)
    }

    interface DataSavedCallback
    {
        fun onDataSaved()
        fun onSaveError()
    }

    fun getEntityDetails(@NotNull entityConstant : Int, entityId : String, callback : DataLoadedCallback)
    fun getEntityList(@NotNull entityConstant : Int, callback : DataLoadedCallback)
}