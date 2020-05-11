# AirMap SDK for Kotlin **(Work In Progress)**

## Setup
- ~Enable `ktlint` pre-commit git hook~ (**Currently not working as `ktlint` does not yet support Kotlin 1.4 syntax (e.g. trailing commas and `fun interface`s**))

### TODO
- Add Maven upload plugin and publish
  - https://github.com/vanniktech/gradle-maven-publish-plugin
  - https://help.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-gradle-for-use-with-github-packages
  - https://github.com/bintray/gradle-bintray-plugin
- Tests
- Tile API
- System Status real time
  - https://github.com/Tinder/Scarlet/
- Traffic real time
  - https://github.com/Tinder/Scarlet/tree/0.2.x (b/c MQTT)
- Telemetry (gRPC)
  - https://github.com/square/wire
  - https://square.github.io/wire/wire_grpc/
- Verify Java compatibility
- Integrate with CI/CD (Bitrise)
- Android Module
  - Fetch config from assets
  - Handle auth flow
  - `LiveData` adapters
  - `Parcelable` support for the data models
    - https://kotlinlang.org/docs/tutorials/android-plugin.html
      - specifically, "Custom Parcelers".
  - Extend Sample with these functionalities
- Separate Mapping modules?
  - MapBox Extensions to convert individual lat+lon into `LatLng` class
  - GMaps Extensions to convert individual lat+lon into `LatLng` class
- Add R8/Proguard rules (esp retrofit and moshi) + README instructions
- Cert Pinning updates?
- Create dummy `AirMapSDK-Java` repo that simply points to this?
  - Same with Android?
- Move `AirMapClient` extension methods into the respective clients and use `JvmDefaults` (interface default methods) so that the interface declaration is closer to usage site)
- Create extension methods for coroutines
- Create a better sample
- Look into making the request execution more seamless
