package com.denisbeck.tmdbmovieapp.networking

import com.denisbeck.tmdbmovieapp.BuildConfig
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { providePopularMoviesApi(get()) }
    factory { provideGenresApi(get()) }
    factory { provideMovieApi(get()) }
    factory { provideCreditsApi(get()) }
    single { provideRetrofit(get()) }
    factory { ResponseHandler() }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.API_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun providePopularMoviesApi(retrofit: Retrofit): PopularMoviesApi = retrofit.create(PopularMoviesApi::class.java)

fun provideGenresApi(retrofit: Retrofit): GenresApi = retrofit.create(GenresApi::class.java)

fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

fun provideCreditsApi(retrofit: Retrofit): CreditsApi = retrofit.create(CreditsApi::class.java)