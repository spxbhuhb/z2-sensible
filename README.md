[![Maven Central](https://img.shields.io/maven-central/v/hu.simplexion.z2/z2-sensible)](https://mvnrepository.com/artifact/hu.simplexion.z2/z2-sensible)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
![Kotlin](https://img.shields.io/github/languages/top/spxbhuhb/z2-sensible)

Sensible is a Kotlin compiler plugin that generates empty constructors with sensible default values.

Sensible is in **I'll write it because I'm sick of defaults** status.

Example:

```kotlin
@Sensible
class A(val i : Int, val s : String) {
    constructor() : this(0, "")  // generated automatically
}
```

Don't get me wrong. I love the strict requirements of Kotlin. However, I hate adding default values to 
constructors when they are trivial. Hence, the plugin.

## Dependencies

Not available yet. They will come eventually, when I figure out why it does not add the constructors.

### Values

1. Any nullable types are initialized to `null`.
2. Any non-nullable types are initialized as in the table below.

| Data Type | Sensible Default |
|-----------|------------------|
| `Boolean` | `false`          |
| `Int`     | `0`              |
| `Long`    | `0L`             |
| `Double`  | `Double.NaN`     |
| `String`  | `""`             |

## License

> Copyright (c) 2020-2023 Simplexion Kft, Hungary and contributors
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this work except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.