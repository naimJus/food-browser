# Food Search App

Food Browser App is a Kotlin-based Android application designed to help users search for healthy food items from various sources. It follows a modular architecture, providing flexibility and maintainability.

## Features

- **Search:** Users can search for food items based on keywords.
- **Error Handling:** Robust error handling for various scenarios like network issues, data parsing errors, and item not found.
- **Caching:** The app caches search results for improved performance and offline usage.
- **Clean Architecture:** The codebase is structured using clean architecture principles for better separation of concerns.

## Architecture

The app is structured with the following components:

- **Data Layer:** Consists of data sources like `FoodDataSource` and `FoodService` responsible for fetching food items, allowing for easy integration with different data sources.
- **Domain Layer:** Defines the use cases, repositories, and entities. The `FoodRepository` communicates with data sources, and the `UseCase` orchestrates business logic.
- **Presentation Layer:** The Android ViewModel (`SearchViewModel`) handles UI-related logic and interacts with the use case to perform searches.
- **UI Components:** The UI includes a Search Activity, which utilizes the `SearchViewModel` to display search results.

## Technology Stack

- **Kotlin:** The project is written in Kotlin.
- **Coroutines:** Used for asynchronous programming.
- **Dagger:** Dependency injection for managing dependencies.
- **Jetpack Compose:** Modern Android UI toolkit for building native UI.

## Prerequisites

Before running the app, ensure you have:

- Android Studio installed
- An Android device or emulator configured

## Installation

1. Clone the repository:

```bash
git clone https://github.com/naimJus/food-browser.git
```
- Open the project in Android Studio. 
- Build and run the app on your device or emulator.

## Usage
- Open the app on your device.
- Enter a search query in the search bar.
- View the search results.
- Tests 

To run unit tests:
```bash
./gradlew test
```