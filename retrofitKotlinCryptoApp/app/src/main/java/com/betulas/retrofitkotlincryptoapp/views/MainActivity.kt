package com.betulas.retrofitkotlincryptoapp.views

import android.database.Observable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betulas.retrofitkotlincryptoapp.adapter.CryptoAdapter
import com.betulas.retrofitkotlincryptoapp.databinding.ActivityMainBinding
import com.betulas.retrofitkotlincryptoapp.model.CryptoModel
import com.betulas.retrofitkotlincryptoapp.service.CryptoApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() ,CryptoAdapter.Listener{
    private var cryptoRecyclerAdapter:CryptoAdapter?=null
    private lateinit var binding: ActivityMainBinding
    private  val BASE_URL="https://raw.githubusercontent.com/"
    private var cryptoModels:ArrayList<CryptoModel>?=null

    //Disposable
    private var compositeDisposable:CompositeDisposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        compositeDisposable= CompositeDisposable()

        //RecyclerView
        val layoutManager: RecyclerView.LayoutManager=LinearLayoutManager(this@MainActivity)
        binding.recylerView.layoutManager=layoutManager
        gettingData()



        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    }
    private fun gettingData(){
        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()


        val service=retrofit.create(CryptoApi::class.java)
        val call=service.getData()
        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful){
                    //.let== eğer ki body boş gelmediyse bu işlemleri yap
                    response.body()?.let {
                        cryptoModels=ArrayList(it)
                        cryptoModels?.let {
                            cryptoRecyclerAdapter=CryptoAdapter(it,this@MainActivity)
                            binding.recylerView.adapter=cryptoRecyclerAdapter
                        }
                        for(im:CryptoModel in cryptoModels!!){
                            println(im.currency)
                            println(im.price)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
    private fun handleResponse(cryptoList:List<CryptoModel>){

        cryptoModels= ArrayList(cryptoList)
        cryptoModels?.let {
            cryptoRecyclerAdapter= CryptoAdapter(it,this@MainActivity)
            binding.recylerView.adapter=cryptoRecyclerAdapter

        }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked:  ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }

}

