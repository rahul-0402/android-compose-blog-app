## Overview

Android application built with Jetpack Compose, following the MVI (Model-View-Intent) and Clean Architecture principles.

Inspired by the MVI architecture using Kotlin Flows and Channels, as described in this [ProAndroidDev article](https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d).

## Features

- **Authentication**: Users can log in, sign up, and log out using JWT authentication.
- **Comment Interaction**: Users can create and view comments on articles.
- **Follow Users**: Users can follow other users to stay updated on their activities.
- **Article Management**: CRUD operations for articles, with pagination for displaying lists. :construction:
- **Favorites**: Mark articles as favorites for quick access.
- **User Management**: CRU- operations for user profiles :construction:

## Tech Stack

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern Android UI toolkit.
- [Material3](https://m3.material.io/) - Design system and components for Android.
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) - Navigation library for Jetpack Compose.
- [Hilt](https://dagger.dev/hilt/) - Dependency injection library for Android.
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Asynchronous programming.
- [Flows](https://kotlinlang.org/docs/flow.html) - Reactive programming using Kotlin Flow.
- [Retrofit 2](https://square.github.io/retrofit/) - HTTP client for Android.
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Data storage solution for Android.
