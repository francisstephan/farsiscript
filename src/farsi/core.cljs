(ns farsi.core
    (:require
      [reagent.core :as r]
      [farsi.trans]))

;; -------------------------
;; Views

(defn atom-input [value]
  [:input {:type "text"
           :class "small"

           :auto-focus true
           :size 120
           :on-change #(reset! value (-> % .-target .-value farsi.trans/transl))}])

(defn shared-state []
  (let [val (r/atom "")]
    (fn []
      [:div
       [:p "Type on latin keyboard: " [atom-input val]]
       [:p "Get farsi text : "]
       [:p {:class "convert" }  [:br] @val]
       ])))

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [shared-state] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
