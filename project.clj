(defproject stream-handler "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [http-kit "2.3.0"]
                 [org.clojure/core.async "0.4.490"]
                 [org.clojure/data.json "1.0.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [compojure "1.6.1"]
                 [stylefruits/gniazdo "1.1.4"]
                 [metrics-clojure "2.10.0"]
                 [prismatic/schema "1.1.12"]
                 [aero "1.1.3"]]
  :main ^:skip-aot stream-handler.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
