# Minecraft Multilingual Examples

This is an example mod for Minecraft that written in multiple languages and mod loaders. This project aims to better visualize the information between languages and how certain common code can remain similar between loaders for easier maintainability and support.

## Available Examples

| Loader | Language | 1.17.x | 1.18.x |
| :---:  |   :---:  | :---:  |  :---: |
| Forge  |   Java   |   ✔️ |   ✔️|
|        |  Kotlin  |   ✔️ |   ✔️|
|        |  Scala   |   ✔️ |   ✔️|
|        |  Groovy  |   ✔️ |   ✔️|
| Fabric |   Java   |   ✔️ |   ✔️|
|        |  Kotlin  |   ✔️ |   ✔️|
|        |  Scala   |   ✔️ |   ✔️|
|        |  Groovy  |   ✔️ |   ✔️|
| Quilt  |   Java   |   ❌   |   ❌  |
|        |  Kotlin  |   ❌   |   ❌  |
|        |  Scala   |   ❌   |   ❌  |
|        |  Groovy  |   ❌   |   ❌  |

### Supported Versions

All versions supported can be found as a branch on the repository. They will always be written as a subset of the major version being referred to (e.g. all 1.18 snapshots will be under the `1.18.x` branch).

### Supported Languages and Loaders

Each language/loader combination will be considered a sub-project within the gradle environment. Note that only the `buildSrc` and the contents of that sub-project need to be present for it to run, anything not used can be removed. Each sub-project is labeled as `<loader>_<language>_<languageVersion>`. Language version is specified for non Java projects as many different versions of other JVM languages can compile to one Java language.

### License

The entire project is licensed under Creative Commons Zero. That means that all copyright for this project is waived and can be used anywhere. Note that trademarks and patents are not granted, warranty is not provided, and there is no liability on the authors of this project for anything done with it.

## Contributing

PRs made to this repo should be in the JVM language and mod loader you are most comfortable with. PRs should also be made to the latest release version of Minecraft. All PRs have two phases: the initial PR and the port PRs. The initial PR will have the change containing the language and loader of your choosing. Once approved, port PRs will be made to the other languages and loaders. Once they have been approved, the PRs will then be merged at once.
