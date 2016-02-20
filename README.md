# TerraCore
[![Master](https://img.shields.io/badge/branch-master-orange.svg)](https://github.com/TerraGamingNetwork/TerraCore/tree/master) [![Build Status](https://img.shields.io/travis/TerraGamingNetwork/TerraCore/master.svg)](https://travis-ci.org/TerraGamingNetwork/TerraCore/branches) [![Code Quality](https://img.shields.io/codacy/0b0313978f0f4838818430ecfc14f595/master.svg)](https://www.codacy.com/app/groovyben008/TerraCore) [![GitHub tags](https://img.shields.io/github/tag/TerraGamingNetwork/TerraCore.svg)](https://github.com/TerraGamingNetwork/TerraCore/tags)<br />
[![Develop](https://img.shields.io/badge/branch-develop-orange.svg)](https://github.com/TerraGamingNetwork/TerraCore/tree/develop) [![Build Status](https://img.shields.io/travis/TerraGamingNetwork/TerraCore/develop.svg)](https://travis-ci.org/TerraGamingNetwork/TerraCore/branches) [![Code Quality](https://img.shields.io/codacy/0b0313978f0f4838818430ecfc14f595/develop.svg)](https://www.codacy.com/app/groovyben008/TerraCore)


TerraCore is primary a Module manager. It loads and maintains all our plugin modules, such as TerraEssentials. It also contains various services and utilities that are used across multiple independent modules.

The main services consist of the Command Service, the Configuration manager and the Module manager.

## Command Service

The Command Service is a annotation based command processor with automatic argument to parameter resolution. Modules, or other Sponge Plugins, can register Objects to this service, and the methods inside the object, if annotated correctly, will be automatically registered as commands.

An example of a command method is shown bellow.

```java
@Command("home")
@Desc("Teleport to your home.")
@Perm("tc.core.home")
public CommandResult onHome(Context context,
	@Desc("The home index.") Integer delay,
	@Desc("The player who's home to teleport to.") @Perm("tc.core.home.others") Optional<Player> player,
	@Desc("Force the teleport if unsafe.") @Perm("tc.core.home.unsafe") @Alias("-f") Flag<Boolean> force
){
	// Do Stuff Here.
	return CommandResult.success();
}
```

## Building

To build the project, run `mvn clean install` from the root directory. The built jar is located in `target/`.

## Contributing

Thank you for considering contributing to TerraCore. If you would like to help, but don't know what to do, take a look at the [Issues](https://github.com/TerraGamingNetwork/TerraCore/issues) or join us on the Terra Gaming Network [Slack Team](http://slack.terragaming.co.uk).

## License
All TerraCore code is released under the [CC BY-NC-SA 4.0](http://creativecommons.org/licenses/by-nc-sa/4.0// "Attribution-NonCommercial-ShareAlike 4.0 International").
