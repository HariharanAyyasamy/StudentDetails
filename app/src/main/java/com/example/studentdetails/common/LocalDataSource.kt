package common

import org.jetbrains.annotations.NotNull

interface LocalDataSource
{
    interface DataLoadedCallback
    {
        fun onDataLoaded()
        fun onLoadingError(errorCode : Int, errorMsg : String)
    }

    interface DataStoredCallback
    {
        fun onDataSaved()
        fun onSaveError()
    }

    fun getEntityDetails(@NotNull entityConstant : Int, entityId : String, callback : DataLoadedCallback) : K
    fun getEntityList(@NotNull entityConstant : Int, callback : DataLoadedCallback) : K

    fun saveEntityDetails(@NotNull entityConstant: Int, data: K, callback: DataStoredCallback)
    fun saveEntityList(@NotNull entityConstant: Int, data: K, callback: DataStoredCallback)
}