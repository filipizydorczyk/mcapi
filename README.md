![](./extras/logo.png)

This is simple spigot plugin that expose some server data through **REST API** so taht u can make tools to manage your server. All you have to do is downlad latest release `.jar` and paste it to server plugin directory. Once your plugin is on server enabled you can start rest app with

```minecratf
/mcapi-start 7000
```

It will satrt app on port **7000**. You can provide every port you want. If port is not provided it will take **7000** by default. When server is being stopped or reloaded app will stop automaticly and you will have to enable it manulaly. You can also stop it with command.

```minecratf
/mcapi-stop
```

To make any request you have to authorize your user. To do this you need to generate token on minecraft server with

```minecratf
/mcapi-token
```

Then you need to copy this token and use it in **Authorion header** as `Bearer Token`. To browse available enppoints import [`minecraft_api.postman_collection.json`](./extras/minecraft_api.postman_collection.json) to postman. Note that this is side project that I develop when I am bored so ammount of endpoints is limited to total basics. You can see example project taht uses this api in my other [repo](https://github.com/filipizydorczyk/minecraft-spigot-manager) (as long as I have completed this project xD).

# 👩🏽‍🔧 Development

```sh
make init
docker-compose up -d
docker exec -it [containerid] mc_send op [playername] #It will make u op in order to performe /reload command to enable plugin
```

Now u can make changes in code. Once u are done with changes and u want to test them go with:

```sh
make build test
```

Login to server (It should be available on `localhost` on port `25565`) and run this command in console

```
/reload
```

At this point ur plugin should be up on test server.

## Endpoints and testing

To test endpoints in postman u can import collection from file [`minecraft_api.postman_collection.json`](./extras/minecraft_api.postman_collection.json)

This app also uses **Server-Sent Events (SSE)** to send events. U can register for it on `/events` endpoint. To test just can run `./sse-client-test.sh` (only on linux or wsl). It will listen for upcoming events and print them. U need to provide op player token to access it. To do so define variable `TOKEN=token ./sse-client-test.sh` u can also define port this way `PORT=7000 ./sse-client-test.sh`.

## Linting

I added config file fot java [checkstyle](https://checkstyle.sourceforge.io/) `linter.xml`. It is slightly changed [google config file](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml). To check project with rules run

```sh
checkstyle -c ./linter.xml ./**/*.java
```

The only diffence between google config and this is that javadoc comments are not required and Indentation is changed from 2 to 4.

## Why Docker

Docker is used for testing pourposes. If you dont want to use it you can test it on your server.
To make container make sure port `25565` is not taken and type `docker-compose up -d`.
Create `plugins` directory on your own because when it's created by docker `root` is owner and you will have to use sudo to pase files in it.
On linux you can type `make init`.
When you paste plugin into this directory it will be available on server.

To test plugin you can restart docker after plugin paste or type `/reload` on server. To be able to type this you need to give op to player.

```bash
docker exec -it [containerid] mc_send op [playername]
```

## Why Makefile

Makefile is just here to automate coping generated jar to `plugins`. Obviously on windows it won't work unless you use **wsl**.

-   make build - generate jar file from project
-   make test - copy generated jar to `plugins`. If file is already in directory it removes old one first
-   make clean - clears `plugins` directory
-   make init - creates `plugins` dir so that you can paste plugin there (otherwise it will be owned by `root`)

Thanks to that you can update plugin on Docker server with one command

```bash
make build test
```

On windows you need to create shell script or copy it manually
