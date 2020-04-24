# AirMap SDK for Kotlin

## Setup
- ~Enable `ktlint` pre-commit git hook~ (*Currently not working as `ktlint` does not yet support Kotlin 1.4 syntax (e.g. trailing commas and `fun interface`s)

### TODO
- Error handling
- Paging for some requests
- Tests
- Automatically refresh token
- KDoc comments
  - copy descriptions from https://developers.airmap.com/reference
  - https://kotlinlang.org/docs/reference/kotlin-doc.html
- Add new Scheduled Airspace endpoints and models
- Verify Java compatibility
- Standardize the use of optionals and default values
  - Should all values be `Optional`/`null`able?
    - Argument for Yes: to prevent breakage if anything is not working
    - Argument for No: when we know a certain field *must* be populated
  - Default value of "" for `String`s?
  - Default value of false for `Boolean`s?
  - Default value of `listOf()` for `List`s? Or `mutableListOf()`?
  - Default value of `mapOf()` for `Map`s? Or `mutableMapOf()`?
- Create specific types for the IDs
  - `inline class`
  - `TypeAlias`
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
- Add Bintray upload logic
- Polish `README.md` and update for customer consumption
- Add R8/Proguard rules (esp retrofit and moshi) + README instructions
- Cert Pinning updates?
- Publish to Bintray
- Create dummy `AirMapSDK-Java` repo that simply points to this?
  - Same with Android?
- Move `AirMapClient` extension methods into the respective clients and use `JvmDefaults` (interface default methods) so that the interface declaration is closer to usage site)
- Create extension methods for coroutines
- Create a better sample
- Look into making the request execution more seamless
