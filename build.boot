(set-env!
 :source-paths   #{"src/main/java"}
 :dependencies '[[org.postgresql/postgresql "42.1.4.jre7"]])

(task-options!
 javac {:options ["-Xlint"]})

(deftask build
  "Recompile files and rerun tests on file changes."
  []
  (comp
   (watch)
   (javac)))
