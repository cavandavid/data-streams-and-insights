(ns stream-handler.server
  (:require [org.httpkit.server :refer [run-server
                                        on-close
                                        on-receive
                                        with-channel]]
            [stream-handler.metrics :refer [mark-event
                                            get-metrics-handler]]
            [aero.core :as aero]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]

            [clojure.data.json :as json]
            [schema.core :as s]
            [taoensso.timbre :as t]))

(def stored-config
  (aero/read-config
   (clojure.java.io/resource "config.edn")))

(defn handle-stream
  "Processes stream and fires event incase of query match"
  [parsed-stream]
  (doseq [query (:queries stored-config)]
    (let [query-passes? (->> parsed-stream
                             (filter (fn [tweet]
                                       (every? (fn [[identifier value]]
                                                 (= (identifier tweet) value)) (dissoc query :count :id))))
                             count
                             (<=  (:count query)))]
      (when query-passes?
        (mark-event (:id query))))))

(defn handle-ws-connection
  "Provides functionality to handle incoming stream connection"
  [{:keys [remote-addr]
    :as request}]
  (t/info "New streaming connection received from" remote-addr)
  (with-channel request channel
    (on-close
     channel
     (fn [_]
       (t/warn "Closing connection for" remote-addr)))

    (on-receive
     channel
     (fn [raw]
       (let [parsed-stream (json/read-str raw :key-fn keyword)]
         (try
           (s/validate [{:username s/Str
                         :hashtag  s/Str
                         :content  s/Str
                         :location s/Str
                         :mood     s/Str}]
                       parsed-stream)
           (t/trace "Received data from" remote-addr "of size " (count parsed-stream))
           (handle-stream parsed-stream)
           (catch java.lang.RuntimeException ex
             (mark-event :corrupted-stream)
             (t/warn "Corrupted data received from stream from" remote-addr))))))))

(defroutes app-routes ;(3)
  (GET "/connect" [] handle-ws-connection)
  (GET "/stats" [] get-metrics-handler)
  (not-found "You Must Be New Here"))

(defn start-web-app
  [port]
  (let [config {:port   port
                :thread 10}]
    (run-server app-routes config)
    (t/info "Server started at port " port)))
