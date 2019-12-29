# server / client apps

Jars could be found in out/client/ and out/server/

Running server with «-slow» argument slows transactions. 
It can be used for demonstration of server waiting for sockets to finish their transactions and close, before server itself finally stops.


# progress

Works:
- post / get image (one at a time) requests from client to server
- multiple clients
- when server stops, it waits until all transactions are finished
- getting an image from browser (through localhost:8080)

Doesn't work:
- posting an image from browser 

