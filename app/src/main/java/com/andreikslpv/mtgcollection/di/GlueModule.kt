package com.andreikslpv.mtgcollection.di

import com.andreikslpv.common_impl.ActivityRequired
import com.andreikslpv.sign_in.domain.repositories.SignInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet

@Module
@InstallIn(SingletonComponent::class)
class GlueModule {

    @Provides
    @ElementsIntoSet
    fun provideActivityRequiredSet(
        adapterAuthRepository: SignInRepository,
    ): Set<@JvmSuppressWildcards ActivityRequired> {
        val set = hashSetOf<ActivityRequired>()
        if (adapterAuthRepository is ActivityRequired) set.add(adapterAuthRepository)
        return set
    }
}