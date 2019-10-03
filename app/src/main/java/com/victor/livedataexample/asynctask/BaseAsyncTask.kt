package com.victor.livedataexample.asynctask

import android.os.AsyncTask

class BaseAsyncTask<T>(
    private val quandoInicia: () -> T,
    private val quandoFinaliza: (resultado: T) -> Unit
) : AsyncTask<Void, Void, T>() {

    override fun doInBackground(vararg p0: Void?): T =  quandoInicia()

    override fun onPostExecute(result: T) {
        super.onPostExecute(result)
        quandoFinaliza(result)
    }

}
