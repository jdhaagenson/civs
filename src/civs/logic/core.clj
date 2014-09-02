(ns
  ^{:author ftomassetti}
  civs.logic.core
  (:require
    [civs.model.core :refer :all]
    [civs.logic.basic :refer :all]
    [civs.logic.tribe-choices :refer :all]
    [civs.logic.demographics :refer :all]))

(defn generate-game [world n-tribes]
  (let [ game (create-game world)
         game (reduce (fn [acc, _] (:game (generate-tribe acc))) game (repeat n-tribes :just_something))]
    game))

(defn- clean-game
  "Remove dead tribes"
  [game]
  (let [tribes-map         (.tribes game)
        updated-tribes-map (select-keys tribes-map (for [[id tribe] tribes-map :when (alive? tribe)] id))]
    (assoc game :tribes updated-tribes-map)))

(defn turn [game]
  (let [ tribes (vals (.tribes game))]
    (clean-game (reduce (fn [acc t] (tribe-turn acc t)) game tribes))))
