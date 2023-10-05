Nicolas Dalpé
Hao Yuan Zhang

Repo: https://github.com/Nicolasd0/IFT3913-TP1


Compilation des différente classes:
exemple avec tloc, c'est le même pour tout les fichiers.
1- Naviger où la classe principale se situe ex: IFT3913-TP1\tloc\src\tloc
2- Exécuter la commande suivante: javac tloc.java


Mode d'utilisation des fichiers .jar
- tloc.jar

  Commande à éxecuter dans le dossier contenant le fichier jar: java –jar tloc.jar PATH_TO_FILE
  Où PATH_TO_FILE est le chemin du fichier pour lequel vous voulez évaluer la mesure tloc.
  
  La sortie est la mesure tloc basé sur le fichier fournis en entrée.

- tassert.jar

  Commande à éxecuter dans le dossier contenant le fichier jar: java –jar tassert.jar PATH_TO_FILE
  Où PATH_TO_FILE est le chemin du fichier pour lequel vous voulez évaluer la mesure tassert.
  
  La sortie est la mesure tassert basé sur le fichier fournis en entrée.


- tls.jar

  Commande à éxecuter dans le dossier contenant le fichier jar: java –jar tassert.jar PATH_TO_FILE
  Où PATH_TO_FILE est le chemin du fichier pour lequel vous voulez compter la mesure tassert.
  
  La sortie est la mesure tassert basé sur le fichier fournis en entrée.

- tls.jar

  Commande à éxecuter dans le dossier contenant le fichier jar: java –jar tls.jar -o CSV_OUTPUT_PATH PATH_TO_FOLDER
  Où CSV_OUTPUT_PATH est le chemin optionnel du fichier csv pour la sortie du résultat.
  et PATH_TO_FILE est le chemin du dossier pour lequel vous voulez le tls.

  
- tropcomp.jar

  Commande à éxecuter dans le dossier contenant le fichier jar: java –jar tropcomp.jar -o CSV_OUTPUT_PATH PATH_TO_FOLDER SEUIL
  Où CSV_OUTPUT_PATH est le chemin optionnel du fichier csv pour la sortie du résultat.
  et PATH_TO_FILE est le chemin du dossier pour lequel vous voulez le tls
  et SEUIL est le seuil en pourcentage des fichiers qui pourrait être considéré comme trop compliqué.
  
  Même sortie que pour tls, mais inclue seulement les fichiers considéré comme trop compliqué


Chaque projet a seulement une ou deux fonctions chaque, donc la modification du code ne devrait pas être très difficile à faire

IMPORTANT!
Pour ce qui est du code, il est important de noter que la classe tls et la classe tcomp utilisent d'autres classes sous 
forme de librairie.
Donc s'il y a modification de la classe tloc par exemple. 
Il est important de recompiler le fichier jar de cette classe et l'ajouter aux librairies du projet tls 
(le dossier "lib" du projet en question).