(ns clj-ssq.core-test-data)

;; Test data directly transcribed from the Valve wiki
(def info-query
  (byte-array
   [0xFF 0xFF 0xFF 0xFF 0x54 0x53 0x6F 0x75 0x72 0x63 0x65
    0x20 0x45 0x6E 0x67 0x69 0x6E 0x65 0x20 0x51 0x75 0x65
    0x72 0x79 0x00]))

(def info-response
  (byte-array
   [0xFF 0xFF 0xFF 0xFF 0x49 0x02 0x67 0x61 0x6D 0x65 0x32
    0x78 0x73 0x2E 0x63 0x6F 0x6D 0x20 0x43 0x6F 0x75 0x6E
    0x74 0x65 0x72 0x2D 0x53 0x74 0x72 0x69 0x6B 0x65 0x20
    0x53 0x6F 0x75 0x72 0x63 0x65 0x20 0x23 0x31 0x00 0x64
    0x65 0x5F 0x64 0x75 0x73 0x74 0x00 0x63 0x73 0x74 0x72
    0x69 0x6B 0x65 0x00 0x43 0x6F 0x75 0x6E 0x74 0x65 0x72
    0x2D 0x53 0x74 0x72 0x69 0x6B 0x65 0x3A 0x20 0x53 0x6F
    0x75 0x72 0x63 0x65 0x00 0xF0 0x00 0x05 0x10 0x04 0x64
    0x6C 0x00 0x00 0x31 0x2E 0x30 0x2E 0x30 0x2E 0x32 0x32
    0x00]))

(def info-response-value
  {:vac-enabled? 0
   :max-players 16
   :edf #{}
   :protocol 2
   :game "Counter-Strike: Source"
   :folder "cstrike"
   :name "game2xs.com Counter-Strike Source #1"
   :bots 4
   :id 240
   :players 5
   :environment 108
   :server-type 100
   :version "1.0.0.22"
   :map "de_dust"
   :visibility 0})

(def player-challenge-query
  (byte-array
   [0xFF 0xFF 0xFF 0xFF 0x55 0xFF 0xFF 0xFF 0xFF]))

(def player-challenge-response
  (byte-array
   [0xFF 0xFF 0xFF 0xFF 0x41 0x4B 0xA1 0xD5 0x22]))

(def player-query
  (byte-array
   [0xFF 0xFF 0xFF 0xFF 0x55 0x4B 0xA1 0xD5 0x22]))

(def player-response
  (byte-array
   [0xFF 0xFF 0xFF 0xFF 0x44 0x02 0x01 0x5B 0x44 0x5D 0x2D 0x2D 0x2D
    0x2D 0x3E 0x54 0x2E 0x4E 0x2E 0x57 0x3C 0x2D 0x2D 0x2D 0x2D 0x00
    0x0E 0x00 0x00 0x00 0xB4 0x97 0x00 0x44 0x02 0x4B 0x69 0x6C 0x6C
    0x65 0x72 0x20 0x21 0x21 0x21 0x00 0x05 0x00 0x00 0x00 0x69 0x24
    0xD9 0x43]))

(def player-response-value
  [{:index 1 :name "[D]---->T.N.W<----" :score 14
    :duration (Float/intBitsToFloat 0x440097B4)}
   {:index 2 :name "Killer !!!" :score 5
    :duration (Float/intBitsToFloat 0x43d92469)}])

(def rules-challenge-query
  (byte-array
   [0xFF 0xFF 0xFF 0xFF 0x56 0xFF 0xFF 0xFF 0xFF]))

(def rules-challenge-response
  (byte-array
   [0xFF 0xFF 0xFF 0xFF 0x41 0x4B 0xA1 0xD5 0x22]))

(def rules-query
  (byte-array
   [0xFF 0xFF 0xFF 0xFF 0x56 0x4B 0xA1 0xD5 0x22]))

;; NOTE: Rules response not transcribed from wiki; as the wiki example
(def rules-response
  [(byte-array
    [0xfe 0xff 0xff 0xff 0x99 0x26 0x00 0x00 0x03 0x00 0xe0 0x04 0xff
     0xff 0xff 0xff 0x45 0x92 0x00 0x62 0x61 0x63 0x6b 0x70 0x61 0x63
     0x6b 0x5f 0x74 0x66 0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e 0x00
     0x32 0x2e 0x31 0x31 0x2e 0x31 0x00 0x62 0x61 0x73 0x69 0x63 0x64
     0x6f 0x6e 0x61 0x74 0x6f 0x72 0x5f 0x76 0x65 0x72 0x73 0x69 0x6f
     0x6e 0x00 0x30 0x2e 0x34 0x00 0x63 0x6f 0x6e 0x6e 0x65 0x63 0x74
     0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x32 0x2e
     0x30 0x00 0x63 0x6f 0x6f 0x70 0x00 0x30 0x00 0x64 0x65 0x61 0x74
     0x68 0x6d 0x61 0x74 0x63 0x68 0x00 0x31 0x00 0x64 0x65 0x63 0x61
     0x6c 0x66 0x72 0x65 0x71 0x75 0x65 0x6e 0x63 0x79 0x00 0x33 0x30
     0x00 0x68 0x6c 0x78 0x63 0x65 0x5f 0x70 0x6c 0x75 0x67 0x69 0x6e
     0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x36 0x2e
     0x31 0x39 0x00 0x68 0x6c 0x78 0x63 0x65 0x5f 0x76 0x65 0x72 0x73
     0x69 0x6f 0x6e 0x00 0x31 0x2e 0x36 0x2e 0x31 0x39 0x00 0x68 0x6c
     0x78 0x63 0x65 0x5f 0x77 0x65 0x62 0x70 0x61 0x67 0x65 0x00 0x68
     0x74 0x74 0x70 0x3a 0x2f 0x2f 0x70 0x72 0x6f 0x62 0x61 0x62 0x6c
     0x79 0x61 0x73 0x65 0x72 0x76 0x65 0x72 0x2e 0x63 0x6f 0x6d 0x2f
     0x73 0x74 0x61 0x74 0x73 0x00 0x6d 0x65 0x74 0x61 0x6d 0x6f 0x64
     0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x31 0x30
     0x2e 0x34 0x56 0x00 0x6d 0x70 0x5f 0x61 0x6c 0x6c 0x6f 0x77 0x4e
     0x50 0x43 0x73 0x00 0x31 0x00 0x6d 0x70 0x5f 0x61 0x75 0x74 0x6f
     0x63 0x72 0x6f 0x73 0x73 0x68 0x61 0x69 0x72 0x00 0x31 0x00 0x6d
     0x70 0x5f 0x61 0x75 0x74 0x6f 0x74 0x65 0x61 0x6d 0x62 0x61 0x6c
     0x61 0x6e 0x63 0x65 0x00 0x31 0x00 0x6d 0x70 0x5f 0x64 0x69 0x73
     0x61 0x62 0x6c 0x65 0x5f 0x72 0x65 0x73 0x70 0x61 0x77 0x6e 0x5f
     0x74 0x69 0x6d 0x65 0x73 0x00 0x30 0x00 0x6d 0x70 0x5f 0x66 0x61
     0x64 0x65 0x74 0x6f 0x62 0x6c 0x61 0x63 0x6b 0x00 0x30 0x00 0x6d
     0x70 0x5f 0x66 0x61 0x6c 0x6c 0x64 0x61 0x6d 0x61 0x67 0x65 0x00
     0x30 0x00 0x6d 0x70 0x5f 0x66 0x6c 0x61 0x73 0x68 0x6c 0x69 0x67
     0x68 0x74 0x00 0x30 0x00 0x6d 0x70 0x5f 0x66 0x6f 0x6f 0x74 0x73
     0x74 0x65 0x70 0x73 0x00 0x31 0x00 0x6d 0x70 0x5f 0x66 0x6f 0x72
     0x63 0x65 0x61 0x75 0x74 0x6f 0x74 0x65 0x61 0x6d 0x00 0x30 0x00
     0x6d 0x70 0x5f 0x66 0x6f 0x72 0x63 0x65 0x72 0x65 0x73 0x70 0x61
     0x77 0x6e 0x00 0x31 0x00 0x6d 0x70 0x5f 0x66 0x72 0x61 0x67 0x6c
     0x69 0x6d 0x69 0x74 0x00 0x30 0x00 0x6d 0x70 0x5f 0x66 0x72 0x69
     0x65 0x6e 0x64 0x6c 0x79 0x66 0x69 0x72 0x65 0x00 0x30 0x00 0x6d
     0x70 0x5f 0x68 0x69 0x67 0x68 0x6c 0x61 0x6e 0x64 0x65 0x72 0x00
     0x30 0x00 0x6d 0x70 0x5f 0x68 0x6f 0x6c 0x69 0x64 0x61 0x79 0x5f
     0x6e 0x6f 0x67 0x69 0x66 0x74 0x73 0x00 0x30 0x00 0x6d 0x70 0x5f
     0x6d 0x61 0x74 0x63 0x68 0x5f 0x65 0x6e 0x64 0x5f 0x61 0x74 0x5f
     0x74 0x69 0x6d 0x65 0x6c 0x69 0x6d 0x69 0x74 0x00 0x30 0x00 0x6d
     0x70 0x5f 0x6d 0x61 0x78 0x72 0x6f 0x75 0x6e 0x64 0x73 0x00 0x30
     0x00 0x6d 0x70 0x5f 0x72 0x65 0x73 0x70 0x61 0x77 0x6e 0x77 0x61
     0x76 0x65 0x74 0x69 0x6d 0x65 0x00 0x31 0x30 0x2e 0x30 0x00 0x6d
     0x70 0x5f 0x73 0x63 0x72 0x61 0x6d 0x62 0x6c 0x65 0x74 0x65 0x61
     0x6d 0x73 0x5f 0x61 0x75 0x74 0x6f 0x00 0x31 0x00 0x6d 0x70 0x5f
     0x73 0x63 0x72 0x61 0x6d 0x62 0x6c 0x65 0x74 0x65 0x61 0x6d 0x73
     0x5f 0x61 0x75 0x74 0x6f 0x5f 0x77 0x69 0x6e 0x64 0x69 0x66 0x66
     0x65 0x72 0x65 0x6e 0x63 0x65 0x00 0x32 0x00 0x6d 0x70 0x5f 0x73
     0x74 0x61 0x6c 0x65 0x6d 0x61 0x74 0x65 0x5f 0x65 0x6e 0x61 0x62
     0x6c 0x65 0x00 0x31 0x00 0x6d 0x70 0x5f 0x73 0x74 0x61 0x6c 0x65
     0x6d 0x61 0x74 0x65 0x5f 0x6d 0x65 0x6c 0x65 0x65 0x6f 0x6e 0x6c
     0x79 0x00 0x30 0x00 0x6d 0x70 0x5f 0x74 0x65 0x61 0x6d 0x6c 0x69
     0x73 0x74 0x00 0x68 0x67 0x72 0x75 0x6e 0x74 0x3b 0x73 0x63 0x69
     0x65 0x6e 0x74 0x69 0x73 0x74 0x00 0x6d 0x70 0x5f 0x74 0x65 0x61
     0x6d 0x70 0x6c 0x61 0x79 0x00 0x30 0x00 0x6d 0x70 0x5f 0x74 0x69
     0x6d 0x65 0x6c 0x69 0x6d 0x69 0x74 0x00 0x30 0x00 0x6d 0x70 0x5f
     0x74 0x6f 0x75 0x72 0x6e 0x61 0x6d 0x65 0x6e 0x74 0x00 0x30 0x00
     0x6d 0x70 0x5f 0x74 0x6f 0x75 0x72 0x6e 0x61 0x6d 0x65 0x6e 0x74
     0x5f 0x72 0x65 0x61 0x64 0x79 0x6d 0x6f 0x64 0x65 0x00 0x30 0x00
     0x6d 0x70 0x5f 0x74 0x6f 0x75 0x72 0x6e 0x61 0x6d 0x65 0x6e 0x74
     0x5f 0x72 0x65 0x61 0x64 0x79 0x6d 0x6f 0x64 0x65 0x5f 0x63 0x6f
     0x75 0x6e 0x74 0x64 0x6f 0x77 0x6e 0x00 0x31 0x30 0x00 0x6d 0x70
     0x5f 0x74 0x6f 0x75 0x72 0x6e 0x61 0x6d 0x65 0x6e 0x74 0x5f 0x72
     0x65 0x61 0x64 0x79 0x6d 0x6f 0x64 0x65 0x5f 0x6d 0x69 0x6e 0x00
     0x32 0x00 0x6d 0x70 0x5f 0x74 0x6f 0x75 0x72 0x6e 0x61 0x6d 0x65
     0x6e 0x74 0x5f 0x72 0x65 0x61 0x64 0x79 0x6d 0x6f 0x64 0x65 0x5f
     0x74 0x65 0x61 0x6d 0x5f 0x73 0x69 0x7a 0x65 0x00 0x30 0x00 0x6d
     0x70 0x5f 0x74 0x6f 0x75 0x72 0x6e 0x61 0x6d 0x65 0x6e 0x74 0x5f
     0x73 0x74 0x6f 0x70 0x77 0x61 0x74 0x63 0x68 0x00 0x31 0x00 0x6d
     0x70 0x5f 0x77 0x65 0x61 0x70 0x6f 0x6e 0x73 0x74 0x61 0x79 0x00
     0x30 0x00 0x6d 0x70 0x5f 0x77 0x69 0x6e 0x64 0x69 0x66 0x66 0x65
     0x72 0x65 0x6e 0x63 0x65 0x00 0x30 0x00 0x6d 0x70 0x5f 0x77 0x69
     0x6e 0x64 0x69 0x66 0x66 0x65 0x72 0x65 0x6e 0x63 0x65 0x5f 0x6d
     0x69 0x6e 0x00 0x30 0x00 0x6d 0x70 0x5f 0x77 0x69 0x6e 0x6c 0x69
     0x6d 0x69 0x74 0x00 0x30 0x00 0x6e 0x61 0x74 0x69 0x76 0x65 0x76
     0x6f 0x74 0x65 0x73 0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e 0x00
     0x30 0x2e 0x38 0x2e 0x33 0x00 0x6e 0x65 0x78 0x74 0x6c 0x65 0x76
     0x65 0x6c 0x00 0x00 0x72 0x5f 0x41 0x69 0x72 0x62 0x6f 0x61 0x74
     0x56 0x69 0x65 0x77 0x44 0x61 0x6d 0x70 0x65 0x6e 0x44 0x61 0x6d
     0x70 0x00 0x31 0x2e 0x30 0x00 0x72 0x5f 0x41 0x69 0x72 0x62 0x6f
     0x61 0x74 0x56 0x69 0x65 0x77 0x44 0x61 0x6d 0x70 0x65 0x6e 0x46
     0x72 0x65 0x71 0x00 0x37 0x2e 0x30 0x00 0x72 0x5f 0x41 0x69 0x72
     0x62 0x6f 0x61 0x74 0x56 0x69 0x65 0x77 0x5a 0x48 0x65 0x69 0x67
     0x68 0x74 0x00 0x30 0x2e 0x30 0x00 0x72 0x5f 0x4a 0x65 0x65 0x70
     0x56 0x69 0x65 0x77 0x44 0x61 0x6d 0x70 0x65 0x6e 0x44 0x61 0x6d
     0x70 0x00 0x31 0x2e 0x30 0x00 0x72 0x5f 0x4a 0x65 0x65 0x70 0x56
     0x69 0x65 0x77 0x44 0x61 0x6d 0x70 0x65 0x6e 0x46 0x72 0x65 0x71
     0x00 0x37 0x2e 0x30 0x00 0x72 0x5f 0x4a 0x65 0x65 0x70 0x56 0x69
     0x65 0x77 0x5a 0x48 0x65 0x69 0x67 0x68 0x74 0x00 0x31 0x30 0x2e
     0x30 0x00 0x72 0x5f 0x56 0x65 0x68 0x69 0x63 0x6c 0x65 0x56 0x69
     0x65 0x77 0x44 0x61 0x6d 0x70 0x65 0x6e 0x00 0x31 0x00 0x73 0x63
     0x63 0x5f 0x70 0x61 0x73 0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e
     0x00 0x32 0x2e 0x32 0x2e 0x30 0x2d 0x70 0x61 0x73 0x00 0x73 0x63
     0x70 0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e 0x00 0x32 0x2e 0x31
     0x2e 0x30 0x00 0x73 0x6d 0x5f 0x61 0x64 0x64 0x74 0x69 0x6d 0x65
     0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x30])
   (byte-array
    [0xfe 0xff 0xff 0xff 0x99 0x26 0x00 0x00 0x03 0x01 0xe0 0x04 0x2e
     0x37 0x31 0x00 0x73 0x6d 0x5f 0x61 0x69 0x61 0x5f 0x76 0x65 0x72
     0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x33 0x2e 0x39 0x00 0x73 0x6d
     0x5f 0x67 0x6c 0x6f 0x62 0x61 0x6c 0x63 0x68 0x61 0x74 0x5f 0x76
     0x65 0x72 0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x31 0x2e 0x30 0x00
     0x73 0x6d 0x5f 0x67 0x6f 0x64 0x6d 0x6f 0x64 0x65 0x5f 0x76 0x65
     0x72 0x73 0x69 0x6f 0x6e 0x00 0x32 0x2e 0x33 0x2e 0x31 0x00 0x73
     0x6d 0x5f 0x6e 0x65 0x78 0x74 0x6d 0x61 0x70 0x00 0x63 0x70 0x5f
     0x64 0x75 0x73 0x74 0x62 0x6f 0x77 0x6c 0x00 0x73 0x6d 0x5f 0x70
     0x61 0x73 0x61 0x66 0x6b 0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e
     0x00 0x31 0x2e 0x30 0x00 0x73 0x6d 0x5f 0x70 0x61 0x73 0x63 0x6f
     0x72 0x65 0x5f 0x61 0x64 0x76 0x65 0x72 0x74 0x69 0x73 0x65 0x6d
     0x65 0x6e 0x74 0x73 0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e 0x00
     0x31 0x2e 0x31 0x2e 0x32 0x00 0x73 0x6d 0x5f 0x70 0x61 0x73 0x63
     0x6f 0x72 0x65 0x5f 0x70 0x6c 0x61 0x79 0x65 0x72 0x73 0x5f 0x76
     0x65 0x72 0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x30 0x2e 0x30 0x00
     0x73 0x6d 0x5f 0x70 0x61 0x73 0x63 0x6f 0x72 0x65 0x5f 0x76 0x65
     0x72 0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x30 0x2e 0x30 0x00 0x73
     0x6d 0x5f 0x70 0x61 0x73 0x76 0x6f 0x74 0x65 0x73 0x5f 0x76 0x65
     0x72 0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x31 0x2e 0x34 0x2d 0x70
     0x61 0x73 0x00 0x73 0x6d 0x5f 0x73 0x70 0x72 0x61 0x79 0x5f 0x76
     0x65 0x72 0x73 0x69 0x6f 0x6e 0x00 0x35 0x2e 0x38 0x61 0x00 0x73
     0x6d 0x5f 0x77 0x69 0x6e 0x5f 0x70 0x61 0x6e 0x65 0x6c 0x5f 0x76
     0x65 0x72 0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x36 0x2e 0x30 0x00
     0x73 0x6f 0x75 0x72 0x63 0x65 0x6d 0x6f 0x64 0x5f 0x76 0x65 0x72
     0x73 0x69 0x6f 0x6e 0x00 0x31 0x2e 0x37 0x2e 0x33 0x2d 0x64 0x65
     0x76 0x2b 0x35 0x32 0x31 0x33 0x00 0x73 0x74 0x65 0x61 0x6d 0x74
     0x6f 0x6f 0x6c 0x73 0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e 0x00
     0x30 0x2e 0x39 0x2e 0x31 0x2b 0x37 0x32 0x30 0x32 0x38 0x33 0x36
     0x00 0x73 0x75 0x70 0x65 0x72 0x6c 0x6f 0x67 0x73 0x5f 0x74 0x66
     0x5f 0x76 0x65 0x72 0x73 0x69 0x6f 0x6e 0x00 0x32 0x2e 0x30 0x2e
     0x33 0x32 0x00 0x73 0x76 0x5f 0x61 0x63 0x63 0x65 0x6c 0x65 0x72
     0x61 0x74 0x65 0x00 0x31 0x30 0x00 0x73 0x76 0x5f 0x61 0x69 0x72
     0x61 0x63 0x63 0x65 0x6c 0x65 0x72 0x61 0x74 0x65 0x00 0x31 0x30
     0x00 0x73 0x76 0x5f 0x61 0x6c 0x6c 0x74 0x61 0x6c 0x6b 0x00 0x31
     0x00 0x73 0x76 0x5f 0x62 0x6f 0x75 0x6e 0x63 0x65 0x00 0x30 0x00
     0x73 0x76 0x5f 0x63 0x68 0x65 0x61 0x74 0x73 0x00 0x30 0x00 0x73
     0x76 0x5f 0x63 0x6f 0x6e 0x74 0x61 0x63 0x74 0x00 0x63 0x6f 0x6e
     0x74 0x61 0x63 0x74 0x40 0x70 0x72 0x6f 0x62 0x61 0x62 0x6c 0x79
     0x61 0x73 0x65 0x72 0x76 0x65 0x72 0x2e 0x63 0x6f 0x6d 0x00 0x73
     0x76 0x5f 0x66 0x6f 0x6f 0x74 0x73 0x74 0x65 0x70 0x73 0x00 0x31
     0x00 0x73 0x76 0x5f 0x66 0x72 0x69 0x63 0x74 0x69 0x6f 0x6e 0x00
     0x34 0x00 0x73 0x76 0x5f 0x67 0x72 0x61 0x76 0x69 0x74 0x79 0x00
     0x38 0x30 0x30 0x00 0x73 0x76 0x5f 0x6d 0x61 0x78 0x73 0x70 0x65
     0x65 0x64 0x00 0x33 0x32 0x30 0x00 0x73 0x76 0x5f 0x6d 0x61 0x78
     0x75 0x73 0x72 0x63 0x6d 0x64 0x70 0x72 0x6f 0x63 0x65 0x73 0x73
     0x74 0x69 0x63 0x6b 0x73 0x00 0x32 0x34 0x00 0x73 0x76 0x5f 0x6e
     0x6f 0x63 0x6c 0x69 0x70 0x61 0x63 0x63 0x65 0x6c 0x65 0x72 0x61
     0x74 0x65 0x00 0x35 0x00 0x73 0x76 0x5f 0x6e 0x6f 0x63 0x6c 0x69
     0x70 0x73 0x70 0x65 0x65 0x64 0x00 0x35 0x00 0x73 0x76 0x5f 0x70
     0x61 0x73 0x73 0x77 0x6f 0x72 0x64 0x00 0x30 0x00 0x73 0x76 0x5f
     0x70 0x61 0x75 0x73 0x61 0x62 0x6c 0x65 0x00 0x30 0x00 0x73 0x76
     0x5f 0x72 0x65 0x67 0x69 0x73 0x74 0x72 0x61 0x74 0x69 0x6f 0x6e
     0x5f 0x6d 0x65 0x73 0x73 0x61 0x67 0x65 0x00 0x00 0x73 0x76 0x5f
     0x72 0x65 0x67 0x69 0x73 0x74 0x72 0x61 0x74 0x69 0x6f 0x6e 0x5f
     0x73 0x75 0x63 0x63 0x65 0x73 0x73 0x66 0x75 0x6c 0x00 0x31 0x00
     0x73 0x76 0x5f 0x72 0x6f 0x6c 0x6c 0x61 0x6e 0x67 0x6c 0x65 0x00
     0x30 0x00 0x73 0x76 0x5f 0x72 0x6f 0x6c 0x6c 0x73 0x70 0x65 0x65
     0x64 0x00 0x32 0x30 0x30 0x00 0x73 0x76 0x5f 0x73 0x70 0x65 0x63
     0x61 0x63 0x63 0x65 0x6c 0x65 0x72 0x61 0x74 0x65 0x00 0x35 0x00
     0x73 0x76 0x5f 0x73 0x70 0x65 0x63 0x6e 0x6f 0x63 0x6c 0x69 0x70
     0x00 0x31 0x00 0x73 0x76 0x5f 0x73 0x70 0x65 0x63 0x73 0x70 0x65
     0x65 0x64 0x00 0x33 0x00 0x73 0x76 0x5f 0x73 0x74 0x65 0x61 0x6d
     0x67 0x72 0x6f 0x75 0x70 0x00 0x00 0x73 0x76 0x5f 0x73 0x74 0x65
     0x70 0x73 0x69 0x7a 0x65 0x00 0x31 0x38 0x00 0x73 0x76 0x5f 0x73
     0x74 0x6f 0x70 0x73 0x70 0x65 0x65 0x64 0x00 0x31 0x30 0x30 0x00
     0x73 0x76 0x5f 0x74 0x61 0x67 0x73 0x00 0x48 0x4c 0x73 0x74 0x61
     0x74 0x73 0x58 0x3a 0x43 0x45 0x2c 0x5f 0x72 0x65 0x67 0x69 0x73
     0x74 0x65 0x72 0x65 0x64 0x2c 0x61 0x6c 0x6c 0x74 0x61 0x6c 0x6b
     0x2c 0x62 0x61 0x63 0x6b 0x70 0x61 0x63 0x6b 0x2e 0x74 0x66 0x2c
     0x63 0x70 0x2c 0x72 0x65 0x70 0x6c 0x61 0x79 0x73 0x00 0x73 0x76
     0x5f 0x76 0x6f 0x69 0x63 0x65 0x65 0x6e 0x61 0x62 0x6c 0x65 0x00
     0x31 0x00 0x73 0x76 0x5f 0x76 0x6f 0x74 0x65 0x5f 0x71 0x75 0x6f
     0x72 0x75 0x6d 0x5f 0x72 0x61 0x74 0x69 0x6f 0x00 0x30 0x2e 0x36
     0x00 0x73 0x76 0x5f 0x77 0x61 0x74 0x65 0x72 0x61 0x63 0x63 0x65
     0x6c 0x65 0x72 0x61 0x74 0x65 0x00 0x31 0x30 0x00 0x73 0x76 0x5f
     0x77 0x61 0x74 0x65 0x72 0x66 0x72 0x69 0x63 0x74 0x69 0x6f 0x6e
     0x00 0x31 0x00 0x74 0x66 0x5f 0x61 0x6c 0x6c 0x6f 0x77 0x5f 0x70
     0x6c 0x61 0x79 0x65 0x72 0x5f 0x75 0x73 0x65 0x00 0x30 0x00 0x74
     0x66 0x5f 0x61 0x72 0x65 0x6e 0x61 0x5f 0x63 0x68 0x61 0x6e 0x67
     0x65 0x5f 0x6c 0x69 0x6d 0x69 0x74 0x00 0x31 0x00 0x74 0x66 0x5f
     0x61 0x72 0x65 0x6e 0x61 0x5f 0x66 0x69 0x72 0x73 0x74 0x5f 0x62
     0x6c 0x6f 0x6f 0x64 0x00 0x31 0x00 0x74 0x66 0x5f 0x61 0x72 0x65
     0x6e 0x61 0x5f 0x66 0x6f 0x72 0x63 0x65 0x5f 0x63 0x6c 0x61 0x73
     0x73 0x00 0x30 0x00 0x74 0x66 0x5f 0x61 0x72 0x65 0x6e 0x61 0x5f
     0x6d 0x61 0x78 0x5f 0x73 0x74 0x72 0x65 0x61 0x6b 0x00 0x33 0x00
     0x74 0x66 0x5f 0x61 0x72 0x65 0x6e 0x61 0x5f 0x6f 0x76 0x65 0x72
     0x72 0x69 0x64 0x65 0x5f 0x63 0x61 0x70 0x5f 0x65 0x6e 0x61 0x62
     0x6c 0x65 0x5f 0x74 0x69 0x6d 0x65 0x00 0x2d 0x31 0x00 0x74 0x66
     0x5f 0x61 0x72 0x65 0x6e 0x61 0x5f 0x70 0x72 0x65 0x72 0x6f 0x75
     0x6e 0x64 0x5f 0x74 0x69 0x6d 0x65 0x00 0x31 0x30 0x00 0x74 0x66
     0x5f 0x61 0x72 0x65 0x6e 0x61 0x5f 0x72 0x6f 0x75 0x6e 0x64 0x5f
     0x74 0x69 0x6d 0x65 0x00 0x30 0x00 0x74 0x66 0x5f 0x61 0x72 0x65
     0x6e 0x61 0x5f 0x75 0x73 0x65 0x5f 0x71 0x75 0x65 0x75 0x65 0x00
     0x31 0x00 0x74 0x66 0x5f 0x62 0x65 0x74 0x61 0x5f 0x63 0x6f 0x6e
     0x74 0x65 0x6e 0x74 0x00 0x30 0x00 0x74 0x66 0x5f 0x62 0x69 0x72
     0x74 0x68 0x64 0x61 0x79 0x00 0x30 0x00 0x74 0x66 0x5f 0x62])
   (byte-array
    [0xfe 0xff 0xff 0xff 0x99 0x26 0x00 0x00 0x03 0x02 0xe0 0x04 0x6f
     0x74 0x5f 0x63 0x6f 0x75 0x6e 0x74 0x00 0x30 0x00 0x74 0x66 0x5f
     0x63 0x6c 0x61 0x73 0x73 0x6c 0x69 0x6d 0x69 0x74 0x00 0x30 0x00
     0x74 0x66 0x5f 0x63 0x74 0x66 0x5f 0x62 0x6f 0x6e 0x75 0x73 0x5f
     0x74 0x69 0x6d 0x65 0x00 0x31 0x30 0x00 0x74 0x66 0x5f 0x64 0x61
     0x6d 0x61 0x67 0x65 0x5f 0x64 0x69 0x73 0x61 0x62 0x6c 0x65 0x73
     0x70 0x72 0x65 0x61 0x64 0x00 0x31 0x00 0x74 0x66 0x5f 0x66 0x6f
     0x72 0x63 0x65 0x5f 0x68 0x6f 0x6c 0x69 0x64 0x61 0x79 0x73 0x5f
     0x6f 0x66 0x66 0x00 0x30 0x00 0x74 0x66 0x5f 0x67 0x61 0x6d 0x65
     0x6d 0x6f 0x64 0x65 0x5f 0x61 0x72 0x65 0x6e 0x61 0x00 0x30 0x00
     0x74 0x66 0x5f 0x67 0x61 0x6d 0x65 0x6d 0x6f 0x64 0x65 0x5f 0x63
     0x70 0x00 0x31 0x00 0x74 0x66 0x5f 0x67 0x61 0x6d 0x65 0x6d 0x6f
     0x64 0x65 0x5f 0x63 0x74 0x66 0x00 0x30 0x00 0x74 0x66 0x5f 0x67
     0x61 0x6d 0x65 0x6d 0x6f 0x64 0x65 0x5f 0x6d 0x76 0x6d 0x00 0x30
     0x00 0x74 0x66 0x5f 0x67 0x61 0x6d 0x65 0x6d 0x6f 0x64 0x65 0x5f
     0x70 0x61 0x79 0x6c 0x6f 0x61 0x64 0x00 0x30 0x00 0x74 0x66 0x5f
     0x67 0x61 0x6d 0x65 0x6d 0x6f 0x64 0x65 0x5f 0x72 0x64 0x00 0x30
     0x00 0x74 0x66 0x5f 0x67 0x61 0x6d 0x65 0x6d 0x6f 0x64 0x65 0x5f
     0x73 0x64 0x00 0x30 0x00 0x74 0x66 0x5f 0x6d 0x61 0x78 0x5f 0x63
     0x68 0x61 0x72 0x67 0x65 0x5f 0x73 0x70 0x65 0x65 0x64 0x00 0x37
     0x35 0x30 0x00 0x74 0x66 0x5f 0x6d 0x65 0x64 0x69 0x65 0x76 0x61
     0x6c 0x00 0x30 0x00 0x74 0x66 0x5f 0x6d 0x65 0x64 0x69 0x65 0x76
     0x61 0x6c 0x5f 0x61 0x75 0x74 0x6f 0x72 0x70 0x00 0x31 0x00 0x74
     0x66 0x5f 0x6d 0x6d 0x5f 0x73 0x65 0x72 0x76 0x65 0x72 0x6d 0x6f
     0x64 0x65 0x00 0x30 0x00 0x74 0x66 0x5f 0x6d 0x6d 0x5f 0x73 0x74
     0x72 0x69 0x63 0x74 0x00 0x30 0x00 0x74 0x66 0x5f 0x6d 0x6d 0x5f
     0x74 0x72 0x75 0x73 0x74 0x65 0x64 0x00 0x30 0x00 0x74 0x66 0x5f
     0x6d 0x76 0x6d 0x5f 0x64 0x65 0x61 0x74 0x68 0x5f 0x70 0x65 0x6e
     0x61 0x6c 0x74 0x79 0x00 0x30 0x00 0x74 0x66 0x5f 0x6d 0x76 0x6d
     0x5f 0x6d 0x69 0x6e 0x5f 0x70 0x6c 0x61 0x79 0x65 0x72 0x73 0x5f
     0x74 0x6f 0x5f 0x73 0x74 0x61 0x72 0x74 0x00 0x33 0x00 0x74 0x66
     0x5f 0x6f 0x76 0x65 0x72 0x74 0x69 0x6d 0x65 0x5f 0x6e 0x61 0x67
     0x00 0x30 0x00 0x74 0x66 0x5f 0x70 0x6c 0x61 0x79 0x65 0x72 0x67
     0x69 0x62 0x00 0x31 0x00 0x74 0x66 0x5f 0x70 0x6c 0x61 0x79 0x65
     0x72 0x5f 0x6e 0x61 0x6d 0x65 0x5f 0x63 0x68 0x61 0x6e 0x67 0x65
     0x5f 0x74 0x69 0x6d 0x65 0x00 0x36 0x30 0x00 0x74 0x66 0x5f 0x70
     0x6f 0x77 0x65 0x72 0x75 0x70 0x5f 0x6d 0x6f 0x64 0x65 0x00 0x30
     0x00 0x74 0x66 0x5f 0x73 0x65 0x72 0x76 0x65 0x72 0x5f 0x69 0x64
     0x65 0x6e 0x74 0x69 0x74 0x79 0x5f 0x64 0x69 0x73 0x61 0x62 0x6c
     0x65 0x5f 0x71 0x75 0x69 0x63 0x6b 0x70 0x6c 0x61 0x79 0x00 0x30
     0x00 0x74 0x66 0x5f 0x73 0x70 0x65 0x63 0x5f 0x78 0x72 0x61 0x79
     0x00 0x31 0x00 0x74 0x66 0x5f 0x73 0x70 0x65 0x6c 0x6c 0x73 0x5f
     0x65 0x6e 0x61 0x62 0x6c 0x65 0x64 0x00 0x30 0x00 0x74 0x66 0x5f
     0x74 0x65 0x61 0x6d 0x74 0x61 0x6c 0x6b 0x00 0x31 0x00 0x74 0x66
     0x5f 0x75 0x73 0x65 0x5f 0x66 0x69 0x78 0x65 0x64 0x5f 0x77 0x65
     0x61 0x70 0x6f 0x6e 0x73 0x70 0x72 0x65 0x61 0x64 0x73 0x00 0x30
     0x00 0x74 0x66 0x5f 0x77 0x65 0x61 0x70 0x6f 0x6e 0x5f 0x63 0x72
     0x69 0x74 0x69 0x63 0x61 0x6c 0x73 0x00 0x31 0x00 0x74 0x66 0x5f
     0x77 0x65 0x61 0x70 0x6f 0x6e 0x5f 0x63 0x72 0x69 0x74 0x69 0x63
     0x61 0x6c 0x73 0x5f 0x6d 0x65 0x6c 0x65 0x65 0x00 0x31 0x00 0x74
     0x68 0x69 0x72 0x64 0x70 0x65 0x72 0x73 0x6f 0x6e 0x5f 0x76 0x65
     0x72 0x73 0x69 0x6f 0x6e 0x00 0x32 0x2e 0x31 0x2e 0x30 0x00 0x74
     0x76 0x5f 0x65 0x6e 0x61 0x62 0x6c 0x65 0x00 0x30 0x00 0x74 0x76
     0x5f 0x70 0x61 0x73 0x73 0x77 0x6f 0x72 0x64 0x00 0x30 0x00 0x74
     0x76 0x5f 0x72 0x65 0x6c 0x61 0x79 0x70 0x61 0x73 0x73 0x77 0x6f
     0x72 0x64 0x00 0x30 0x00])])

(def rules-response-value
  {"mp_scrambleteams_auto" "1",
   "sv_contact" "contact@probablyaserver.com",
   "mp_winlimit" "0",
   "mp_scrambleteams_auto_windifference" "2",
   "sm_aia_version" "1.3.9",
   "tf_beta_content" "0",
   "hlxce_plugin_version" "1.6.19",
   "nextlevel" "",
   "r_JeepViewDampenFreq" "7.0",
   "mp_disable_respawn_times" "0",
   "r_JeepViewZHeight" "10.0",
   "mp_match_end_at_timelimit" "0",
   "tf_mm_trusted" "0",
   "scp_version" "2.1.0",
   "metamod_version" "1.10.4V",
   "sv_registration_successful" "1",
   "sv_accelerate" "10",
   "sv_steamgroup" "",
   "sv_footsteps" "1",
   "sv_cheats" "0",
   "tf_player_name_change_time" "60",
   "sv_waterfriction" "1",
   "tf_powerup_mode" "0",
   "sm_pascore_version" "1.0.0",
   "mp_tournament_readymode_countdown" "10",
   "tv_relaypassword" "0",
   "coop" "0",
   "sv_stopspeed" "100",
   "tf_mm_servermode" "0",
   "tf_medieval" "0",
   "tf_arena_first_blood" "1",
   "tf_arena_force_class" "0",
   "tf_weapon_criticals" "1",
   "tf_mvm_death_penalty" "0",
   "sv_rollspeed" "200",
   "mp_autoteambalance" "1",
   "tf_arena_change_limit" "1",
   "tf_weapon_criticals_melee" "1",
   "hlxce_webpage" "http://probablyaserver.com/stats",
   "mp_timelimit" "0",
   "tf_arena_use_queue" "1",
   "mp_flashlight" "0",
   "mp_tournament_readymode" "0",
   "connect_version" "1.2.0",
   "mp_highlander" "0",
   "tf_bot_count" "0",
   "mp_friendlyfire" "0",
   "tf_use_fixed_weaponspreads" "0",
   "sm_win_panel_version" "1.6.0",
   "tf_max_charge_speed" "750",
   "mp_teamlist" "hgrunt;scientist",
   "sv_tags" "HLstatsX:CE,_registered,alltalk,backpack.tf,cp,replays",
   "tf_playergib" "1",
   "nativevotes_version" "0.8.3",
   "deathmatch" "1",
   "tf_server_identity_disable_quickplay" "0",
   "tf_gamemode_payload" "0",
   "sv_alltalk" "1",
   "tf_ctf_bonus_time" "10",
   "tv_enable" "0",
   "tf_damage_disablespread" "1",
   "tf_overtime_nag" "0",
   "tf_spec_xray" "1",
   "sm_pascore_players_version" "1.0.0",
   "sm_pasafk_version" "1.0",
   "mp_respawnwavetime" "10.0",
   "sv_specnoclip" "1",
   "sm_globalchat_version" "1.1.0",
   "tf_medieval_autorp" "1",
   "sv_gravity" "800",
   "sm_addtime_version" "1.0.71",
   "mp_tournament_readymode_team_size" "0",
   "mp_tournament_readymode_min" "2",
   "mp_forceautoteam" "0",
   "tf_gamemode_sd" "0",
   "tf_gamemode_rd" "0",
   "mp_windifference_min" "0",
   "decalfrequency" "30",
   "mp_allowNPCs" "1",
   "sm_nextmap" "cp_dustbowl",
   "sv_friction" "4",
   "tf_arena_preround_time" "10",
   "mp_falldamage" "0",
   "tf_gamemode_ctf" "0",
   "superlogs_tf_version" "2.0.32",
   "sv_noclipspeed" "5",
   "sv_maxusrcmdprocessticks" "24",
   "mp_windifference" "0",
   "basicdonator_version" "0.4",
   "sv_pausable" "0",
   "mp_tournament" "0",
   "sv_rollangle" "0",
   "tf_classlimit" "0",
   "mp_holiday_nogifts" "0",
   "sv_maxspeed" "320",
   "sm_godmode_version" "2.3.1",
   "tf_force_holidays_off" "0",
   "r_VehicleViewDampen" "1",
   "sv_specaccelerate" "5",
   "tf_gamemode_cp" "1",
   "tf_teamtalk" "1",
   "hlxce_version" "1.6.19",
   "sm_spray_version" "5.8a",
   "tv_password" "0",
   "mp_tournament_stopwatch" "1",
   "sv_airaccelerate" "10",
   "sv_password" "0",
   "tf_gamemode_mvm" "0",
   "tf_arena_max_streak" "3",
   "mp_autocrosshair" "1",
   "mp_fraglimit" "0",
   "sm_pascore_advertisements_version" "1.1.2",
   "sv_specspeed" "3",
   "tf_mm_strict" "0",
   "r_AirboatViewDampenDamp" "1.0",
   "tf_arena_round_time" "0",
   "sv_vote_quorum_ratio" "0.6",
   "tf_mvm_min_players_to_start" "3",
   "r_AirboatViewZHeight" "0.0",
   "tf_gamemode_arena" "0",
   "sv_registration_message" "",
   "mp_forcerespawn" "1",
   "backpack_tf_version" "2.11.1",
   "tf_arena_override_cap_enable_time" "-1",
   "mp_fadetoblack" "0",
   "mp_footsteps" "1",
   "tf_allow_player_use" "0",
   "mp_stalemate_enable" "1",
   "sv_noclipaccelerate" "5",
   "sm_pasvotes_version" "1.1.4-pas",
   "mp_teamplay" "0",
   "tf_birthday" "0",
   "mp_weaponstay" "0",
   "sv_voiceenable" "1",
   "sourcemod_version" "1.7.3-dev+5213",
   "mp_stalemate_meleeonly" "0",
   "r_JeepViewDampenDamp" "1.0",
   "sv_bounce" "0",
   "thirdperson_version" "2.1.0",
   "sv_stepsize" "18",
   "mp_maxrounds" "0",
   "sv_wateraccelerate" "10",
   "scc_pas_version" "2.2.0-pas",
   "tf_spells_enabled" "0",
   "r_AirboatViewDampenFreq" "7.0",
   "steamtools_version" "0.9.1+7202836"})