## Process data streams and provide insights based on pre-determined criteria

Handles multiple data streams and provides insights based on the criteria set in config file

### Usage
1. Start web app
```
$ lein run run-app
20-10-02 08:34:08 cavan--E470 INFO [stream-handler.server:71] - Server started at port  9092
```
2. Start multiple connection streams to it by running the following command across multiple instances
```
lein run run-client
```

Insights are available at `http://localhost:9092/stats`


### License

Copyright © 2020 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
