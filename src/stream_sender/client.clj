(ns stream-sender.client
  (:require [gniazdo.core :as ws]
            [clojure.data.json :as json]
            [clojure.core.async :refer [<!!
                                        go
                                        timeout]]
            [stream-sender.data :refer [get-randomized-tweets]]))

(def websocket-conenction-status (atom nil))

(defn create-websocket-connection
  "Establishes websocket connection and returns a ws connection object"
  [url]
  (ws/connect (str "ws://" url)
              :on-connect (fn [& _]
                            (reset! websocket-conenction-status :connected))
              :on-close   (fn [& _]
                            (reset! websocket-conenction-status :disconnected))))

(defn send-stream
  "Simulate a live stream of data"
  [url]
  (while true
    (let [ws-connection-object (create-websocket-connection url)]
      (while (= @websocket-conenction-status :connected)
        (do
          (ws/send-msg ws-connection-object (json/write-str (get-randomized-tweets 50)))
          (<!! (timeout 1500)))))))
