(ns stream-sender.data)

(def users
  "Users who are tweeting"
  ["cavan.david"
   "aravind_baskaran"
   "mark.randomson"])

(def hashtags
  "Hashtags people are tweeting about"
  ["ipl"
   "kohli"
   "bjp"
   "congress"
   "nvidia"
   "amd"])

(def cities
  "Cities from where tweets are coming"
  ["mumbai"
   "delhi"
   "bangalore"
   "kolkata"])

(def mood
  "User's mood while tweeting"
  ["happy"
   "sad"
   "bored"
   "depressed"
   "exhausted"])

; username, hashtag, content, location
(defn get-randomized-tweets
  "Returns collection of tweets"
  [n]
  (repeatedly n #(identity
                  {:username (rand-nth users)
                   :hashtag  (rand-nth hashtags)
                   :mood     (rand-nth mood)
                   :content  (str "While i'm tweeting this i'm " (rand-nth mood))
                   :location (rand-nth cities)})))
