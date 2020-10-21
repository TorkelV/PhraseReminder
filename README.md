# Phrase Reminder

An app implementation that tries to use a pure functional approach.

The main goal is to use as few side effects as possible.
* Most side effects should only affect the database or data store.
* Necessary side effects should be encapsulated as much as possible (such as showing or hiding a view).
* View manipulation should only happen through data binding.

Features and libs:
* Single activity architecture
* Data binding
* LiveData
* Flow
* Room
* androidx DataStore
* Koin

Other neat things:
* Versioning with https://github.com/tslamic/versioning
* Github actions CI
* Spotless / ktlint
* Gradle Kotlin DSL