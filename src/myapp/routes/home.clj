(ns myapp.routes.home
  (:require [compojure.core :refer :all]
            [myapp.views.layout :as layout]
            [hiccup.form :refer :all]))


(defn show-messages []
  [:ul.guests
   (for [{:keys [message name time]}
         [{:message "hi world" :name "Fredy" :time "20:00:20"}
          {:message "hola mundo" :name "Leonel" :time "23:23:23"}]]
     [:li 
      [:p message]
      [:p "User:" [:cite.user name] " - " time]])])


(defn home [& [name message error]]
  (layout/common
   [:h1 "My second chat"]
   [:p "Mensajes"]
   [:p error]
                ;; Firebase content here!!!!!!!!
   (show-messages)
   [:hr]
   (form-to [:post "/"]
            [:p "Name:"]
            (text-field "name" name)
            [:p "Message:"]
            (text-area {:rows 10 :cols 40} "message" message)
            [:br]
            (submit-button "Enviar Mensaje"))))


(defn save-message [name message]
  (cond
   (empty? name)
   (home name message "Some dummy forgot to leave a name")
   (empty? message)
   (home name message "Don't you have something to say?")
   :else
   (do
     (println name message)
     (home))))


(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" [name message] (save-message name message)))
