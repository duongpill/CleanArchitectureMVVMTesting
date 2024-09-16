# Clean Architecture Android Template

This template will help me learn about Clean Architecture, MVVM, and Jetpack Compose.

## Getting Started

To build this project, please ensure you installed the Android environments.

### Demo


### The APK file


## Main Purposes

```
- Using the Clean Architecture and MVVM.
- Using the newest Android libraries.
- Show the Image list by Coil.
- Use the Room to save the data in the local database.
- Show the Images even when using offline mode.
- Show/hide the image details in the animation.
- Have some test cases to make sure the app works perfectly.
```

### Description

I used the newest Android libraries in this project, such as Jetpack Compose, Compose Navigation, Hilt, Room, Retrofit, and Coil.

First of all, I applied CleanArchitecture and MVVM so that I could separate everything independently. Therefore, I divided them into main sections: Domain, Data, and Presentation.

```
- Domain: This one will contain the business logic and the entities around the app.
- Data: We will get the data from the Domain from here.
- Presentation: using MVVM, we will have the ViewModel to manage the UI state easily. I also use ViewModel to communicate between View and Model to get the data and update View immediately.
```

After I had a base structure, I started to code the app:

- I will use Jetpack Compose to create the app UI because it currently supports Android.
- I used the Coil to load the images and showed the Shimmer when the image was loading, then I cached the loaded images in the disk and memory.
- I applied the infinite loading when scrolling the items.
- Show the animation to load the image detail and drag gestures to close it.
- When the app goes offline, we will get the last items in the database and use Bitmap to load the image.

### Testing

I only applied the Unit Test for the Use Case class and did the Integration Test for trying to click the image in the list.
