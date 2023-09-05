package com.plcoding.cryptocurrencyappyt.domain.use_cases.get_coin

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoinDetail
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetail
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.sql.RowId
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke(coinId: String):
            Flow<Resource<CoinDetail>>  = flow{
                try{
                    emit(Resource.Loading())
                    val coin = repository.getCoinById(coinId).toCoinDetail()
                    emit(Resource.Success<CoinDetail>(coin))
                }
                catch (e: HttpException){
                    emit(Resource.Error<String>(e.localizedMessage,"An unexpected error occured"))

                }catch (e: IOException){
                    emit(Resource.Error<String>(e.localizedMessage,"Couldn't reach server. Check your internet connection"))
                }

            } as Flow<Resource<CoinDetail>>
}