I created 2 classes, 
    1.ServerConfiguration which contains socket address and some welcome message,
        it is singleton class can only be initiated with reflection API, otherwise it's not possible.
        I am wondering, Is this thread-safe ? Okay, it depends on Reflection API execution of constructor is safe. If reflection API executes constructor atomically, when static variable holding instance will not change but new instance created will be garbage collected because no one is holding that reference. But Reflection Constructor API is not thread safe, so it is not thread safe.
    2. WebServer, it has end point and it starts the server.
In Main class, 
    we initiate creation of ServerConfig object and hold it in static variable of that class.
    and start the server