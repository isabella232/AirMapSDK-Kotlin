# AirMap SDK for Kotlin

## Setup
- Enable ktlint pre-commit git hook

### TODO
- Tests
- Verify Java compatibility
- Error handling
- Standardize the use of optionals and default values
  - Should all values be Optional/nullable?
    - Argument for Yes: to prevent breakage if anything is not working
    - Argument for No: when we know a certain field *must* be populated
  - Default value of "" for Strings?
  - Default value of false for Booleans?
  - Default value of listOf() for lists? Or mutableListOf()?
  - Default value of mapOf() for Maps? Or mutableMapOf()?
- Create specific types for the IDs
  - Inline Class
  - TypeAlias
- Create annotation+adapter for automatically converting list to csv
  - https://github.com/highthunder/kotlr/blob/3ceb15caf7cbcec63b6794d1e82e1d7aa3fbf997/src/main/kotlin/com.highthunder.kotlr/json/qualifier/CommaSeparatedStringJsonAdapter.kt
- KDoc comments
  - copy descriptions from https://developers.airmap.com/reference
  - https://kotlinlang.org/docs/reference/kotlin-doc.html
- Integrate with CI/CD (Bitrise)
- Rename sdk to core (?)
- Create extension methods for coroutines
- Create android specific module
  - Fetch config from assets
  - LiveData adapters
  - Parcelable support for the data models
    - https://kotlinlang.org/docs/tutorials/android-plugin.html
      - specifically, "Custom Parcelers".
- Separate Mapping modules?
  - MapBox Extensions to convert individual lat+lon into LatLng class
  - GMaps Extensions to convert individual lat+lon into LatLng class
- Add+Require trailing commas once Kotlin 1.4 is released
- Add Bintray upload logic
- Polish README and update for customer consumption
- Add R8/Proguard rules (esp retrofit and moshi) + README instructions
- Cert Pinning updates?
- Publish to Bintray
- Create dummy AirMapSDK-Java repo that simply points to this?
  - Same with Android?
- Move AirMapClient extension methods into the respective clients and use JvmDefaults (interface default methods) so that the interface declaration is closer to usage site)
- Create a better sample
- Look into making the request execution more seamless
- Paging for some requests
