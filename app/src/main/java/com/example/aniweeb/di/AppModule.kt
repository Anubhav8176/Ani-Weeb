package com.example.aniweeb.di

import android.content.Context
import com.example.aniweeb.R
import com.example.aniweeb.core.model.AnimeResponse
import com.example.aniweeb.core.networking.retrofit.AnimeApiInterface
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val BASE_URL = "https://api.jikan.moe/v4/"

    @Singleton
    @Provides
    fun firebaseAuthProvider(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun firebaseFirestoreProvider(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideGoogleSignClient(
        @ApplicationContext context: Context
    ): GoogleSignInClient{
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, options)
    }

    @Singleton
    @Provides
    fun provideAnimeApiInterface(): AnimeApiInterface{
        val retrofit by lazy {
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit.create(AnimeApiInterface::class.java)
    }

}
