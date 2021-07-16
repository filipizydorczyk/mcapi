![](https://www.minecraft.net/etc.clientlibs/minecraft/clientlibs/main/resources/img/header/logo.png)

# Getting started

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

# Endpoints

To test endpoints in postman u can import collection from file [`minecraft_api.postman_collection.json`](./minecraft_api.postman_collection.json)

# Stack

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
