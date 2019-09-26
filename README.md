# Networkable Minesweeper [STILL UNDER DEVELOPMENT]

The __Networkable Minesweeper__ is a classic minesweeper game, developped in JAVA and playable in network.

## Test
First, you need to compile all the files. In order to do that, run the [compile.sh] file:
```sh
$ sh compile.sh
```

You can also install the fonts used by the app, by running the [fonts.install.sh] file (it is not mandatory):
```sh
$ sh fonts.install.sh
```

Then, if you want to play solo, you just have to run the [client.run.sh] file:
```sh
$ sh client.run.sh
```

If you want to test the client/server part of this app on your computer, you need to run at least a second client, and then to run the [server.run.sh] file. Full example:
```sh
$ sh client.run.sh &
$ sh client.run.sh &
$ sh server.run.sh &
```
Then, to connect to the server, you just need to change your _player name_ (if you are testing only on your computer, you don't need to change the _server name_: __localhost__, or the _server port_: __10000__), and to click on the __Connexion__ button. 

## Developed with
  - Java 12.0.2 2019-07-16
  - Java(TM) SE Runtime Environment (build 12.0.2+10)
  - Java HotSpot(TM) 64-Bit Server VM (build 12.0.2+10, mixed mode, sharing)
  - Ubuntu 18.04.3 LTS

## License
This project is licensed under the MIT License - see the [LICENSE] file for details

   [LICENSE]: <LICENSE>
   [compile.sh]: <compile.sh>
   [server.run.sh]: <server.run.sh>
   [client.run.sh]: <client.run.sh>
   [fonts.install.sh]: <fonts.install.sh>