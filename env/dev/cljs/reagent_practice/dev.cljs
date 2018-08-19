(ns ^:figwheel-no-load reagent-practice.dev
  (:require
    [reagent-practice.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
