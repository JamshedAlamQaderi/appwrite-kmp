# Appwrite KMP SDK
[![Maven Central](https://img.shields.io/maven-central/v/com.jamshedalamqaderi.kmp/appwrite)](https://search.maven.org/artifact/com.jamshedalamqaderi.kmp/appwrite)

A Kotlin Multiplatform (KMP) client for Appwrite, enabling seamless integration with Appwrite's backend services in your KMP projects. This client is designed to work with Android, iOS, and other platforms supported by Kotlin Multiplatform.

**Note:** This repository is truly inspired by the [Appwrite Android SDK](https://github.com/appwrite/sdk-for-android). Every function in this client mirrors the Android SDK to maintain consistency and ease of use.

## Features

- **Full Appwrite Support:** Integrate with all Appwrite services.
- **Cross-Platform:** Easily use the client across Android, iOS, and other platforms.
- **Consistent API:** Every function mirrors the Appwrite Android SDK for ease of use.

## Prerequisites

- A working Kotlin Multiplatform project.
- Android Studio for Android development.
- Xcode for iOS development.
- An Appwrite project with the necessary configurations.

## Installation

### Common Setup

Include the Appwrite KMP client dependency in your shared code. Add the following line to your `CommonMain/build.gradle.kts`:

```kotlin
implementation("com.jamshedalamqaderi.kmp:appwrite-kmp:<LATEST_VERSION>")
```

### Android Setup

1. **Update AndroidManifest.xml**

   Add the following snippet inside the `<application>` tag of your `AndroidManifest.xml` file:

   ```xml
   <activity android:name="com.jamshedalamqaderi.kmp.appwrite.views.CallbackActivity" android:exported="true">
       <intent-filter android:label="android_web_auth">
           <action android:name="android.intent.action.VIEW" />
           <category android:name="android.intent.category.DEFAULT" />
           <category android:name="android.intent.category.BROWSABLE" />
           <data android:scheme="appwrite-callback-[project-id]" />
       </intent-filter>
   </activity>
   ```

   Replace `[project-id]` with your actual Appwrite project ID.

2. **Register Activity Lifecycle Callbacks**

   In your `MainActivity.kt`, add the following line to register the `AppwriteActivityLifecycleCallbacks`:

   ```kotlin
   registerActivityLifecycleCallbacks(AppwriteActivityLifecycleCallbacks)
   ```

### iOS Setup

Modify your `info.plist` to handle Appwrite callbacks by adding the following:

```xml
<key>CFBundleURLTypes</key>
<array>
    <dict>
        <key>CFBundleTypeRole</key>
        <string>Editor</string>
        <key>CFBundleURLName</key>
        <string>io.appwrite</string>
        <key>CFBundleURLSchemes</key>
        <array>
            <string>appwrite-callback-[project-id]</string>
        </array>
    </dict>
</array>
```

Replace `[project-id]` with your actual Appwrite project ID.

## Client Initialization

To initialize the Appwrite client in your project, use the following code snippet:

```kotlin
val client = Client()
  .setEndpoint("https://[HOSTNAME_OR_IP]/v1") // Replace with your API Endpoint
  .setProject("5df5acd0d48c2") // Replace with your Appwrite project ID
  .setSelfSigned(true) // Remove this in production if using self-signed certificates
```

## Known Issues

- **OAuth2 Browser-Based Login on Desktop:**
  Currently, OAuth2 browser-based login does not work on desktop platforms. It is supported only on Android, iOS, and web.

## Contributing

We welcome contributions from the community! To contribute:

1. **Fork the repository.**
2. **Create a new branch** for your feature or bug fix.
3. **Commit your changes** with clear commit messages.
4. **Open a pull request** against the `main` branch.

Please ensure your code adheres to the project's coding standards and includes relevant tests.

## Additional Resources

For more detailed documentation, visit the [Appwrite Official Documentation](https://appwrite.io/docs).

## License

This project is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for full details.

```plaintext
Copyright 2025 Jamshed Alam Qaderi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

