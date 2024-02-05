# StoneFight

각별님의 [석전](https://www.youtube.com/watch?v=FqAD6Z2Ivoo) 플러그인

# Library

* [Kommand](https://github.com/monun/kommand)

# Start

`/sf start` : 게임 시작   
`/sf setting <Team> <Player>` : 플레이어의 팀을 세팅할 수 있음 (기본 값 : 랜덤) 

# Configuration

## 서버 여실때 꼭 아래의 세팅으로 열어주시길 바랍니다

spigot.yml

```yml
merge-radius:
  item: 0
```

# Build

* `./gradlew paperJar`
* `Gradle -> Tasks -> other -> paperJar`

# License

[GPL-3.0](https://github.com/NOOBNUBY/kotlin-plugin-template/blob/master/LICENSE)