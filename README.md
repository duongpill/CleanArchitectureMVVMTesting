# Clean Architecture Android Template

This template aims to help me gain overall knowledge about Clean Architecture, MVVM, and Jetpack Composition.

## Getting Started

To build this project, please ensure you installed the Android environments.

### Demo


### The APK file


## Main Purposes

```
- Using the Clean Architecture and MVVM.
- Using the newest Android libraries.
- Show the Image list by Coil.
- Using the Room to save the data into the local database.
- Show the Images even when using offline mode.
- Show/Hide the image detail by the animation.
- Have some test cases to make sure the app work perfectly.
```

### Description

I used the newest Android libraries in this project, such as Jetpack Compose, Compose Navigation, Hilt, Room, Retrofit, and Coil.

First of all, I applied CleanArchitecture and MVVM so that I can separate everything independently. Therefore, I separated them into some main sections as: Domain, Data, and Presentation.

```
- Domain: This one will contain the business logic and the entities around the app.
- Data: From the Domain, we will decide to get the data from here.
- Presentation: using MVVM, we will have the ViewModel to manage the UI state easily. I also use ViewModel to communicate between View and Model to get the data and update View immediately.
```

After I had a base structure, I started to code the app:

- I will absolutely use Jetpack Compose to do the UI for the app because It is supporting the Android very strong currently.
- I used the Coil to load the images and showed the Shimmer when the image was loading, then I cached the loaded images in the disk and memory.
- I applied the infinite loading when scrolling the items.
- Show the animation to load the image detail and drag gestures to close it.
- When the app is gonna the offline mode, we will get the last items in Database and will use the Bitmap to load the image.

### Testing

I only applied the Unit Test for the Use Case class and did the Integration Test for trying to click the image in the list.