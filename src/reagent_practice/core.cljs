(ns reagent-practice.core
    (:require
     [reagent.core :as r]
     [reagent-practice.bmi-component :as bc]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to Reagent"]
   [bc/bmi-component]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
