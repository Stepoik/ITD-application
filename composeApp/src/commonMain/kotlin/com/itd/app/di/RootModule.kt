package com.itd.app.di

import com.itd.app.core.koin.componentFactory
import com.itd.app.core.koin.componentOf
import com.itd.app.core.ktor.TokenHolder
import com.itd.app.core.ktor.TokenRefresher
import com.itd.app.core.ktor.authorizedKtorClient
import com.itd.app.core.ktor.defaultKtorClient
import com.itd.app.features.auth.ui.AuthComponent
import com.itd.app.features.auth.ui.AuthComponentImpl
import com.itd.app.features.common.api.TrendsRepository
import com.itd.app.features.common.api.UsersRepository
import com.itd.app.features.common.data.TrendsRepositoryImpl
import com.itd.app.features.common.data.UsersRepositoryImpl
import com.itd.app.features.feed.api.PostsRepository
import com.itd.app.features.feed.data.PostsRepositoryImpl
import com.itd.app.features.feed.ui.FeedComponent
import com.itd.app.features.feed.ui.FeedComponentImpl
import com.itd.app.features.feed.ui.list.PostsListComponent
import com.itd.app.features.feed.ui.list.PostsListComponentImpl
import com.itd.app.features.home.HomeComponent
import com.itd.app.features.home.HomeComponentImpl
import com.itd.app.features.notifications.api.NotificationRepository
import com.itd.app.features.notifications.data.NotificationRepositoryImpl
import com.itd.app.features.notifications.ui.NotificationComponent
import com.itd.app.features.notifications.ui.NotificationComponentImpl
import com.itd.app.features.post.api.CommentsRepository
import com.itd.app.features.post.data.CommentsRepositoryImpl
import com.itd.app.features.post.ui.fullpost.FullPostComponent
import com.itd.app.features.post.ui.fullpost.FullPostComponentImpl
import com.itd.app.features.post.ui.fullpost.comments.CommentsComponent
import com.itd.app.features.post.ui.fullpost.comments.CommentsComponentImpl
import com.itd.app.features.post.ui.fullpost.postinfo.PostInfoComponent
import com.itd.app.features.post.ui.fullpost.postinfo.PostInfoComponentImpl
import com.itd.app.features.profile.api.ProfileRepository
import com.itd.app.features.profile.data.ProfileRepositoryImpl
import com.itd.app.features.profile.ui.ProfileComponent
import com.itd.app.features.profile.ui.ProfileComponentImpl
import com.itd.app.features.profile.ui.components.liked.ProfileLikedPostsComponent
import com.itd.app.features.profile.ui.components.liked.ProfileLikedPostsComponentImpl
import com.itd.app.features.profile.ui.components.posts.ProfilePostsComponent
import com.itd.app.features.profile.ui.components.posts.ProfilePostsComponentImpl
import com.itd.app.features.profile.ui.components.profileInfo.ProfileInfoComponent
import com.itd.app.features.profile.ui.components.profileInfo.ProfileInfoComponentImpl
import com.itd.app.features.root.RootComponent
import com.itd.app.features.root.RootComponentImpl
import com.itd.app.features.search.api.SearchRepository
import com.itd.app.features.search.data.SearchRepositoryImpl
import com.itd.app.features.search.ui.SearchComponent
import com.itd.app.features.search.ui.SearchComponentImpl
import com.itd.app.features.splash.SplashComponent
import com.itd.app.features.splash.SplashComponentImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module

val AUTHORIZED_KTOR_QUALIFIER = qualifier("authorized_ktor")
val DEFAULT_KTOR_QUALIFIER = qualifier("default_ktor")

expect val platformModule: Module

val persistenceModule = module {
    includes(platformModule)
}

val networkModule = module {
    singleOf(::TokenHolder)
    single { TokenRefresher(get(qualifier = DEFAULT_KTOR_QUALIFIER)) }
    single(AUTHORIZED_KTOR_QUALIFIER) { authorizedKtorClient(get(), get()) }
    single(DEFAULT_KTOR_QUALIFIER) { defaultKtorClient() }
}

val feedModule = module {
    single<PostsRepository> { PostsRepositoryImpl(get(AUTHORIZED_KTOR_QUALIFIER)) }

    componentOf(::PostsListComponentImpl) bind PostsListComponent::class
    componentOf(::FeedComponentImpl) bind FeedComponent::class
    componentFactory<PostsListComponent.Factory> { PostsListComponentImpl.Factory() }
    componentFactory<FeedComponent.Factory> { FeedComponentImpl.Factory() }
}

val homeModule = module {
    includes(feedModule)

    componentOf(::HomeComponentImpl) bind HomeComponent::class
    componentFactory<HomeComponent.Factory> { HomeComponentImpl.Factory() }
}

val authModule = module {
    componentOf(::AuthComponentImpl) bind AuthComponent::class
    componentFactory<AuthComponent.Factory> { AuthComponentImpl.Factory() }
}

val profileModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get(AUTHORIZED_KTOR_QUALIFIER)) }

    componentOf(::ProfileComponentImpl) bind ProfileComponent::class
    componentFactory<ProfileComponent.Factory> { ProfileComponentImpl.Factory() }

    componentOf(::ProfileInfoComponentImpl) bind ProfileInfoComponent::class
    componentFactory<ProfileInfoComponent.Factory> { ProfileInfoComponentImpl.Factory() }

    componentOf(::ProfilePostsComponentImpl) bind ProfilePostsComponent::class
    componentFactory<ProfilePostsComponent.Factory> { ProfilePostsComponentImpl.Factory() }

    componentOf(::ProfileLikedPostsComponentImpl) bind ProfileLikedPostsComponent::class
    componentFactory<ProfileLikedPostsComponent.Factory> { ProfileLikedPostsComponentImpl.Factory() }
}

val commonModule = module {
    single<TrendsRepository> { TrendsRepositoryImpl(get(AUTHORIZED_KTOR_QUALIFIER)) }
    single<UsersRepository> { UsersRepositoryImpl(get(AUTHORIZED_KTOR_QUALIFIER)) }
}

val searchModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get(AUTHORIZED_KTOR_QUALIFIER)) }

    componentOf(::SearchComponentImpl) bind SearchComponent::class
    componentFactory<SearchComponent.Factory> { SearchComponentImpl.Factory() }
}

val notificationsModule = module {
    single<NotificationRepository> { NotificationRepositoryImpl(get(AUTHORIZED_KTOR_QUALIFIER)) }

    componentOf(::NotificationComponentImpl) bind NotificationComponent::class
    componentFactory<NotificationComponent.Factory> { NotificationComponentImpl.Factory() }
}

val postModule = module {
    componentOf(::FullPostComponentImpl) bind FullPostComponent::class
    componentFactory<FullPostComponent.Factory> { FullPostComponentImpl.Factory() }

    componentOf(::PostInfoComponentImpl) bind PostInfoComponent::class
    componentFactory<PostInfoComponent.Factory> { PostInfoComponentImpl.Factory() }

    componentOf(::CommentsComponentImpl) bind CommentsComponent::class
    componentFactory<CommentsComponent.Factory> { CommentsComponentImpl.Factory() }

    single<CommentsRepository> { CommentsRepositoryImpl(get(AUTHORIZED_KTOR_QUALIFIER)) }
}

val rootModule = module {
    componentOf(::RootComponentImpl) bind RootComponent::class
    componentFactory<RootComponent.Factory> { RootComponentImpl.Factory() }

    componentOf(::SplashComponentImpl) bind SplashComponent::class
    componentFactory<SplashComponent.Factory> { SplashComponentImpl.Factory() }
    includes(
        networkModule,
        persistenceModule,
        homeModule,
        authModule,
        profileModule,
        commonModule,
        searchModule,
        notificationsModule,
        postModule
    )
}