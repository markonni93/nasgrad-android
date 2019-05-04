package com.nasgrad.dagger

import com.nasgrad.NasGradApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(
    modules = [
        ApiModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        AndroidSupportInjectionModule::class]
)

@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: NasGradApplication): Builder

        fun build(): AppComponent
    }

    fun inject(appController: NasGradApplication)
}