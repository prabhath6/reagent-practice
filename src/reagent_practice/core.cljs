(ns reagent-practice.core
    (:require
     [reagent.core :as r]
     [reagent-practice.bmi-component :as bc]
     [reagent-practice.time-component :as tc]
     [reagent-practice.background-color-changer :as bgc]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to Reagent"]
   [bc/bmi-component]
   [tc/timer-component]
   [bgc/bg-color-changer]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
