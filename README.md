# clj-ssq

[![Clojars Project](http://clojars.org/clj-ssq/latest-version.svg)](http://clojars.org/clj-ssq)

A Clojure library implementing the Source Server Query (SSQ) and
Master Server Query protocols for interacting with Source engine
powered game servers (TF2, L4D, etc.).

I recommend glancing over the following Valve dev wiki pages about
SSQ:

https://developer.valvesoftware.com/wiki/Server_queries
https://developer.valvesoftware.com/wiki/Master_Server_Query_Protocol

Note that this library uses futures, so applications using this
library may suffer a 1 minute delay before exiting unless they call
`(shutdown-agents)` or `(System/exit <exit-status>)`. See
http://dev.clojure.org/jira/browse/CLJ-124 for more information.

## Usage

This library provides 3 SSQ query functions: `info`, `players`, and
`rules` (in the `clj-ssq.core` namespace), lining up with the 3
non-deprecated SSQ queries listed on protocol's wiki page. Each of
these functions accepts a hostname/ip address parameter (as a string)
and a port number.

Additionally, the `master` function is provided for querying the
Source master servers (you most likely want
"hl2master.steampowered.com" with port 27011). The `master` function
takes `host` and `port` like the previous functions, along with a
`region` parameter (one of `:us-east :us-west :africa :asia
:middle-east :europe :south-america :australia :other`) and a `filter`
parameter which is a string following the filter format on this page:

https://developer.valvesoftware.com/wiki/Master_Server_Query_Protocol

For example, "\appid\440" would fetch all listed TF2 servers. Be
careful to escape the backslashes, in a program the filter should be
written as "\\appid\\440".

All above-mentioned query functions return a promise that will receive
either the requested data, or an error object of the form `{:err
:timeout}`, `{:err :socket-timeout}`, `{:err :port-unreachable}`,
`{:err :io-exception}`, or `{:err :compression-unsupported}` (the
latter indicating that the response is compressed, a protocol feature
that modern Source engine servers do not use and is not currently
supported by this library). When relevant, error objects may also have
an `:exception` key with the exception that caused the query to fail.

The query functions also accept two optional timeout keyword parameters,
`:timeout` and `:socket-timeout` (given in milliseconds; both default to
3000). `:timeout` is the time required for a complete answer to be
received and delivered to the promise, whereas `:socket-timeout` is
the time allowed for each UDP datagram to arrive (some responses are
split over multiple datagrams, all of which must be received for the
complete answer to be assembled).

## Example

```clojure
user> (def s (clj-ssq.core/info "tf2.example.com" 27015))
#'user/s
user> (pprint @s)
{:vac-enabled? true,
 :password? false,
 :gameid 440N,
 :max-players 24,
 :players 15,
 :protocol 17,
 :game "Team Fortress",
 :folder "tf",
 :name "Our tf2 server title",
 :bots 0,
 :port 27015,
 :keywords {"HLstatsX:CE" "_registered" "replays" "alltalk"}
 :id 440,
 :environment :linux,
 :server-type :dedicated,
 :steamid 81234567898765432N,
 :version "2892667",
 :map "cp_dustbowl",
 :edf #{:steamid? :port? :keywords? :gameid?}
 ;; see the valve wiki; some values will only appear if their flag is
 ;; present in the edf field.
 }
user> (def s (clj-ssq.core/players "tf2.example.com" 27015))
#'user/s
user> (pprint @s)
[{:index 0, :name "taco", :score 10, :duration 4568.4946}
 {:index 0, :name "Player 2", :score 8, :duration 4406.8394}]
user> (def s (clj-ssq.core/rules "tf2.example.com" 27015))
#'user/s
user> (pprint @s)
{"mp_scrambleteams_auto" "1",
 "hlxce_plugin_version" "1.6.19",
 "nextlevel" "",
 "sv_tags" "HLstatsX:CE,_registered,alltalk,backpack.tf,cp,replays",
 "mp_footsteps" "1"
 ...}
user> (def m (clj-ssq.core/master "hl2master.steampowered.com" 27011
                                  :us-east "\\appid\\440"))
#'user/m
user> (pprint @m)
#{"208.78.165.72:27046" "208.78.164.166:27042" "208.78.164.169:27067"
  "74.91.123.49:27015" "68.232.174.111:27015" "216.52.148.207:27015"
  "192.223.31.101:27015" "74.91.119.82:27015" "72.5.195.233:27015"
  ...
  }
user> (def s (clj-ssq.core/rules "127.0.0.1" 1234))
#'user/s
;; Wait 3 seconds
user> @s
{:err :timeout}
```

## License

Copyright © 2015 George Pittarelli

Distributed under the MIT License.
